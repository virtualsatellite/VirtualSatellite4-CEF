/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import de.dlr.sc.virsat.model.concept.list.IBeanList;
import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.general.IUuid;
import de.dlr.sc.virsat.model.dvlm.provider.DVLMEditPlugin;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.roles.Discipline;
import de.dlr.sc.virsat.model.dvlm.roles.IUserContext;
import de.dlr.sc.virsat.model.dvlm.roles.RightsHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.Activator;
import de.dlr.sc.virsat.model.extension.cef.model.ExcelCalculation;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.Value;
import de.dlr.sc.virsat.project.editingDomain.VirSatEditingDomainRegistry;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

/**
 * Synchronizes the data model with the data from an excel file.
 * Input params are read into the file, output params read from it.
 * @author muel_s8
 *
 */

public class ExcelUpdater {
	
	public static final String OVERVIEW_SHEET_NAME = "VS Overview";
	public static final String PARAMS_VIRSAT_2_XL_SHEET_NAME = "VS VirSat2Xl Parameter";
	public static final String PARAMS_XL_2_VIRSAT_SHEET_NAME = "VS Xl2VirSat Parameter";
	private static final String NONE = "";
	
	private static final int OVERVIEW_HEADLINE_ROW = 0;
	private static final int OVERVIEW_PROJECT_NAME_ROW = 1;
	private static final int OVERVIEW_TIMESTAMP_ROW = 2;
	
	private static final int OVERVIEW_HEAD_COLUMN = 0;
	private static final int OVERVIEW_CONTENT_COLUMN = 1;
	
	private static final String OVERVIEW_PROJECT_NAME_HEAD = "Project Name";
	
	private static final String[] PARAMS_SC_HEADERS = { "ID", "SystemComponent Name", "Description", "Assigned Discipline", "ID of parent" };
	private static final int PARAMS_SEI_HEADER_NAMES_ROW = 0;
	private static final int PARAMS_SEI_HEADER_DATA_ROW = 1;
	
	private static final int ID_COLUMN = 0;
	private static final int SYSTEM_COMPONENT_NAME_COLUMN = 1;
	private static final int DESCRIPTION_COLUMN = 2;
	private static final int ASSIGNED_DISCIPLINE_COLUMN = 3;
	private static final int ID_OF_PARENT_COLUMN = 4;
	
	private static final int LIST_OF_INPUT_PARAMETERS_ROW = 3;
	private static final int LIST_OF_PARAMETERS_COLUMN = 0;
	
	private static final String LIST_OF_INPUT_PARAMETERS = "List of Virtual Satellite Parameters";
	
	private static final String[] PARAMS_HEADERS_ID = { "ID", "Parameter Name", "Connected" };
	private static final String[] PARAMS_HEADERS_VALUE = { "Value", "Unit", "Note" };
	private static final int PARAMS_HEADER_NAMES_ROW = 4;
	
	private static final int PARAMETER_NAME_COLUMN = 1;
	private static final int PARAMETER_CONNECTED_COLUMN = 2;
	private static final int PARAMETER_VALUE_COLUMN = 3;
	private static final int PARAMETER_UNIT_COLUMN = 4;
	private static final int PARAMETER_NOTE_COLUMN = 5;

	public static final String PARAMETER_CONNECTED_VALUE_CONNECTED = "Connected";
	public static final String PARAMETER_CONNECTED_VALUE_DISCONNECTED = "Disconnected";
	
	private static final String MODE_VALUE_CLM_OFFSET_VALUE_NA = "N/A";
	private static final int MODE_VALUE_CLM_OFFSET_NOTE = 2;
	private static final int MODE_VALUE_CLM_OFFSET_UNIT = 1;
	private static final int MODE_VALUE_CLM_OFFSET_VALUE = 0;

	private static final String SYSTEM_MODE_HEADER_NAME = "Mode";
	private static final int SYSTEM_MODE_CLM_OFFSET_NAME = 0;
	private static final int SYSTEM_MODE_CLM_OFFSET_MODE = 1;
	
	private IProject project;
	private TransactionalEditingDomain editingDomain;
	private ExcelCalculation virsatXlCalculationBean;
	private Workbook xlWorkbook;
	private ExcelHelper xlHelper;
	
	private Sheet xlSheetOverview;
	private Sheet xlSheetParamsVirSat2Xl;
	private Sheet xlSheetParamsXl2VirSat;
	
	private IUserContext userContext;
	
	/**
	 * Creates an excel updater for each excel calculation attached to the object
	 * and performs it.
	 * @param sei the structural element instance with the attached excel calculations
	 * @param userContext The User Context under which to execute the excel update.
	 */
	public static void performUpdate(StructuralElementInstance sei, IUserContext userContext) {
		VirSatTransactionalEditingDomain editingDomain = VirSatEditingDomainRegistry.INSTANCE.getEd(sei);
		IProject project = editingDomain.getResourceSet().getProject();
		
		BeanCategoryAssignmentHelper beanCaHelper = new BeanCategoryAssignmentHelper();
		
		List<ExcelCalculation> excelCalcs = beanCaHelper.getAllBeanCategories(sei, ExcelCalculation.class);
		
		for (ExcelCalculation excelCalc : excelCalcs) {
			ExcelUpdater updater = new ExcelUpdater(project, editingDomain, excelCalc, userContext);
			if (updater.canUpdateExcelFile()) {
				updater.updateExcelFile();
			}
		}	
	}
	
	/**
	 * Constructor for the excel updater. Links the updater to the excel file
	 * at the specified path.
	 * @param project the project in which the updating process happens
	 * @param editingDomain the editing domain
	 * @param excelCalc the excel calculation we wish to perform
	 * @param userContext  The user Context for running the excel sheet update
	 */
	public ExcelUpdater(IProject project, TransactionalEditingDomain editingDomain, ExcelCalculation excelCalc, IUserContext userContext) {
		this.project = project;
		this.editingDomain = editingDomain;
		this.virsatXlCalculationBean = excelCalc;
		this.userContext = userContext;

		openExcelFile();
	}
	
	/**
	 * Opens an Excel file
	 */
	private void openExcelFile() {
		URI eResourceUri = virsatXlCalculationBean.getExcelFileBean().getValue();
		URIConverter converter = editingDomain.getResourceSet().getURIConverter();
		
		InputStream inputStream;
		
		try {
			inputStream = converter.createInputStream(eResourceUri);
			xlWorkbook = new HSSFWorkbook(inputStream);
		} catch (OfficeXmlFileException e) {
			try {
				inputStream = converter.createInputStream(eResourceUri);
				xlWorkbook = new XSSFWorkbook(inputStream);
			} catch (Exception e2) {
				Activator.getDefault().getLog().log(new Status(Status.WARNING, Activator.PLUGIN_ID, "ExcelUpdater: Failed to open Excel file or not excel file selected."));
			}
		} catch (Exception e) {
			Activator.getDefault().getLog().log(new Status(Status.WARNING, Activator.PLUGIN_ID, "ExcelUpdater: Failed to open Excel file or not excel file selected."));
		}
		
		if (xlWorkbook != null) {
			xlHelper = new ExcelHelper(xlWorkbook);
		}
	}
	
	/**
	 * This method checks that everything is setup correctly for being updated
	 * @return true iff the excel can be indeed updated
	 */
	public boolean canUpdateExcelFile() {
		if (xlWorkbook == null) {
			return false;
		}
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.addAll(virsatXlCalculationBean.getFromExcel2VirSat());
		parameters.addAll(virsatXlCalculationBean.getFromVirSat2Excel());
		
		boolean hasNullParameter = parameters.stream().filter(parameter -> parameter.getTypeInstance() == null).count() > 0;
		if (hasNullParameter) {
			return false;
		}
		
		boolean hasNullMode = parameters.stream()
				.flatMap(parameter -> parameter.getModeValues().stream().map(Value::getMode).filter(mode -> mode == null))
				.count() > 0;
		
		if (hasNullMode) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Performs the update modyfing both the data model and the excel file.
	 */
	public void updateExcelFile() {
		checkAndCreateSheets();
		
		writeSheetOverview();
		
		writeSheetVirSat2XlParams();
		writeSheetXl2VirSatParams();
		
		HSSFFormulaEvaluator.evaluateAllFormulaCells(xlWorkbook);
		
		saveExcelFile();
		processSheetXl2VirSatParams();
	}
	
	/**
	 * Method to handle the creation of the input parameter sheet
	 */
	private void writeSheetXl2VirSatParams() {
		parseSheetParamsHeader(xlSheetParamsXl2VirSat);
		parseSheetParamsValues(xlSheetParamsXl2VirSat);
		writeSheetParamsHeader(xlSheetParamsXl2VirSat);
		writeSheetParamsValues(xlSheetParamsXl2VirSat, true);
		prettifySheetParamsColumns(xlSheetParamsXl2VirSat);
	}

	/**
	 * Method to handle the creation of the ouput parameter sheet
	 */
	private void writeSheetVirSat2XlParams() {
		parseSheetParamsHeader(xlSheetParamsVirSat2Xl);
		parseSheetParamsValues(xlSheetParamsVirSat2Xl);
		writeSheetParamsHeader(xlSheetParamsVirSat2Xl);
		writeSheetParamsValues(xlSheetParamsVirSat2Xl, false);
		prettifySheetParamsColumns(xlSheetParamsVirSat2Xl);
	}

	private Map<String, Integer> excelSystemModesColumIndex = new HashMap<>(); 

	/**
	 * This method reads the given sheet of an XL workbook to see which parameters
	 * have been written and which modes/columns are already present. The information is needed
	 * when writing to the sheets, so the data does not get corrupted.
	 * @param xlSheetParams The ExcelSheet which is either filled with input or output parameters from VirSat
	 */
	private void parseSheetParamsHeader(Sheet xlSheetParams) {
		int modeCounter = 1;
		// First reset the cache of Modes and their columns
		excelSystemModesColumIndex = new HashMap<>();
		Cell cellMode = null;
		Row rowMode = null;

		// Now try to identify the columns which are already present in the given sheet
		do {
			int modeColumnIndexOffset = PARAMS_HEADERS_ID.length + (PARAMS_HEADERS_VALUE.length * modeCounter); 
			rowMode = xlSheetParams.getRow(LIST_OF_INPUT_PARAMETERS_ROW);
			if (rowMode != null) {
				cellMode = rowMode.getCell(modeColumnIndexOffset + SYSTEM_MODE_CLM_OFFSET_MODE);
				if (cellMode != null) {
					String modeName = cellMode.getStringCellValue().trim();
					excelSystemModesColumIndex.put(modeName, modeColumnIndexOffset + SYSTEM_MODE_CLM_OFFSET_NAME);
				}
			}
			modeCounter++;
		} while (rowMode != null && cellMode != null);
	}
	
	private Map<String, Integer> parameterUuidRowIndex;

	/**
	 * This method parses the values of an exiting parameter sheet.
	 * This is needed to later re-detect which parameters have been newly introduced
	 * and which ones are old ones in the tables that need to be updated.
	 * @param xlSheetParams either the input or output sheet
	 */
	private void parseSheetParamsValues(Sheet xlSheetParams) {
		parameterUuidRowIndex = new HashMap<>();
		int row = PARAMS_HEADER_NAMES_ROW + 1;
		Cell cellParamId = null;
		Row rowParamId = null;
		do {
			rowParamId = xlSheetParams.getRow(row);
			if (rowParamId != null) {
				cellParamId = rowParamId.getCell(ID_COLUMN);
				if (cellParamId != null) {
					String parameterUuid = cellParamId.getStringCellValue().trim();
					parameterUuidRowIndex.put(parameterUuid, row);
				}
			}
			row++;
		} while (rowParamId != null && cellParamId != null);
	}
	
	/**
	 * This method hands back or creates a row index for a given parameter. The results are based on the
	 * previous parsing of the row table. Matching of parameters is done yb using their uuids
	 * @param param the parameter for which to get the index
	 * @return the index as integer
	 */
	private int getParameterRowIndex(Parameter param) {
		String uuid = param.getATypeInstance().getUuid().toString();
		if (parameterUuidRowIndex.containsKey(uuid)) {
			return parameterUuidRowIndex.get(uuid);
		} else {
			int newRow = (parameterUuidRowIndex.values().isEmpty())
					? PARAMS_HEADER_NAMES_ROW + 1
					: parameterUuidRowIndex.values().stream().max(Integer::compare).orElseGet(() -> 0) + 1;
			parameterUuidRowIndex.put(uuid, newRow);
			return newRow;
		}
	}
	
	/**
	 * Method to get or create a mode column index. In case a new one needs to be created it is added to
	 * the right of the table. Matching of modes is done by their names
	 * @param systemModeName The Name of the System Mode for which to get a column index
	 * @return an integer with the correct index of the mode
	 */
	private int getModeColumnIndex(String systemModeName) {
		// in case we know already in which column to write, we just write
		// otherwise we need to calculate the new row and remember it
		systemModeName = systemModeName.trim();
		if (excelSystemModesColumIndex.containsKey(systemModeName)) {
			return excelSystemModesColumIndex.get(systemModeName);
		} else {
			// now calculate the new column which is the default * 3 (value unit note) + each used mode * 3
			int modeColumnIndexModeOffset = excelSystemModesColumIndex.size() * PARAMS_HEADERS_VALUE.length;
			int modeColumnIndexParameterIdOffset = PARAMS_HEADERS_ID.length;
			int modeColumnIndexDefaultValueOffset = PARAMS_HEADERS_VALUE.length;
			int modeColumnIndex =  modeColumnIndexParameterIdOffset + modeColumnIndexDefaultValueOffset + modeColumnIndexModeOffset;
			excelSystemModesColumIndex.put(systemModeName.trim(), modeColumnIndex);
			return modeColumnIndex;
		}
	}


	/**
	 * Check if the necessary sheets exist, we want
	 * (0) VirSat Overview
	 * (1) VirSat Params From
	 * (2) VirSat Params To
	 * If they dont exist, create them
	 */
	private void checkAndCreateSheets() {
		if (xlWorkbook.getSheet(OVERVIEW_SHEET_NAME) == null) {
			xlSheetOverview = xlWorkbook.createSheet(OVERVIEW_SHEET_NAME);
		} else {
			xlSheetOverview = xlWorkbook.getSheet(OVERVIEW_SHEET_NAME);
		}
		
		if (xlWorkbook.getSheet(PARAMS_VIRSAT_2_XL_SHEET_NAME) == null) {
			xlSheetParamsVirSat2Xl = xlWorkbook.createSheet(PARAMS_VIRSAT_2_XL_SHEET_NAME);
		} else {
			xlSheetParamsVirSat2Xl = xlWorkbook.getSheet(PARAMS_VIRSAT_2_XL_SHEET_NAME);
		}
		
		if (xlWorkbook.getSheet(PARAMS_XL_2_VIRSAT_SHEET_NAME) == null) {
			xlSheetParamsXl2VirSat = xlWorkbook.createSheet(PARAMS_XL_2_VIRSAT_SHEET_NAME);
		} else {
			xlSheetParamsXl2VirSat = xlWorkbook.getSheet(PARAMS_XL_2_VIRSAT_SHEET_NAME);
		}
	}
	
	/**
	 * Write the overview sheet into the excel sheet VirSat Overview
	 */
	private void writeSheetOverview() {
		Row headlineRow = xlSheetOverview.createRow(OVERVIEW_HEADLINE_ROW);
		Cell headline = headlineRow.createCell(OVERVIEW_HEAD_COLUMN);
		headline.setCellValue(OVERVIEW_SHEET_NAME);
		
		Row projectRow = xlSheetOverview.createRow(OVERVIEW_PROJECT_NAME_ROW);
		Cell projectNameHeadCell = projectRow.createCell(OVERVIEW_HEAD_COLUMN);
		projectNameHeadCell.setCellValue(OVERVIEW_PROJECT_NAME_HEAD); 
		Cell projectNameCell = projectRow.createCell(OVERVIEW_CONTENT_COLUMN);
		projectNameCell.setCellValue(project.getName());
		
		Row timestampRow = xlSheetOverview.createRow(OVERVIEW_TIMESTAMP_ROW);
		Cell timestampHeadCell = timestampRow.createCell(OVERVIEW_HEAD_COLUMN);
		timestampHeadCell.setCellValue(OVERVIEW_PROJECT_NAME_HEAD); 
		Cell timestampCell = timestampRow.createCell(OVERVIEW_CONTENT_COLUMN);
		timestampCell.setCellValue(new Date().toString());
		
		xlSheetOverview.autoSizeColumn(OVERVIEW_HEAD_COLUMN);
		xlSheetOverview.autoSizeColumn(OVERVIEW_CONTENT_COLUMN);
	}
	
	/**
	 * Write the header of the input parameters
	 * @param xlSheetParams 
	 */
	private void writeSheetParamsHeader(Sheet xlSheetParams) {
		// Fill in the header titles
		for (int i = 0; i < PARAMS_SC_HEADERS.length; ++i) {
			xlHelper.setCellValue(xlSheetParams, PARAMS_SC_HEADERS[i], PARAMS_SEI_HEADER_NAMES_ROW, i, ExcelHelper.SC_HEADER);
		}
		
		// Fill in the header data
		CategoryAssignment ca = virsatXlCalculationBean.getTypeInstance();
		StructuralElementInstance sei = (StructuralElementInstance) CategoryAssignmentHelper.getContainerFor(ca);
		Discipline discipline = sei.getAssignedDiscipline();
		IUuid parent = (IUuid) sei.eContainer();
		
		xlHelper.setCellValue(xlSheetParams, sei.getUuid().toString(), PARAMS_SEI_HEADER_DATA_ROW, ID_COLUMN, ExcelHelper.SC_HEADER);
		xlHelper.setCellValue(xlSheetParams, sei.getName(), PARAMS_SEI_HEADER_DATA_ROW, SYSTEM_COMPONENT_NAME_COLUMN, ExcelHelper.SC_HEADER);
		xlHelper.setCellValue(xlSheetParams, sei.getDescription(), PARAMS_SEI_HEADER_DATA_ROW, DESCRIPTION_COLUMN, ExcelHelper.SC_HEADER);
		xlHelper.setCellValue(xlSheetParams, discipline == null ? NONE : discipline.getName(), PARAMS_SEI_HEADER_DATA_ROW, ASSIGNED_DISCIPLINE_COLUMN, ExcelHelper.SC_HEADER);
		xlHelper.setCellValue(xlSheetParams, parent == null ? NONE : parent.getUuid().toString(), PARAMS_SEI_HEADER_DATA_ROW, ID_OF_PARENT_COLUMN, ExcelHelper.SC_HEADER);
	}
	
	/**
	 * Write the input parameters into the excel sheet
	 * @param xlSheetParams either the input or output sheet to write to
	 * @param isXl2VirSat a boolean indicating if it the input or output sheet 
	 */
	private void writeSheetParamsValues(Sheet xlSheetParams, boolean isXl2VirSat) {
		xlHelper.setCellValue(xlSheetParams, LIST_OF_INPUT_PARAMETERS, LIST_OF_INPUT_PARAMETERS_ROW, LIST_OF_PARAMETERS_COLUMN, ExcelHelper.CELL_NORMAL);
		
		// Fill in the header titles
		xlHelper.setCellValues(xlSheetParams, PARAMS_HEADERS_ID, PARAMS_HEADER_NAMES_ROW, 0, ExcelHelper.PARAM_HEADER);
		xlHelper.setCellValues(xlSheetParams, PARAMS_HEADERS_VALUE, PARAMS_HEADER_NAMES_ROW, PARAMS_HEADERS_ID.length, ExcelHelper.PARAM_HEADER);
		
		// Now print a Value Unit and Note Header for every mode that is used
		Set<SystemMode> usedSystemModes = getUsedParameterModes();
		for (SystemMode systemMode : usedSystemModes) {
			int modeColumn = getModeColumnIndex(systemMode.getName()); 

			xlHelper.setCellValue(xlSheetParams, SYSTEM_MODE_HEADER_NAME, LIST_OF_INPUT_PARAMETERS_ROW, modeColumn + SYSTEM_MODE_CLM_OFFSET_NAME, ExcelHelper.CELL_NORMAL);
			xlHelper.setCellValue(xlSheetParams, systemMode.getName(), LIST_OF_INPUT_PARAMETERS_ROW, modeColumn + SYSTEM_MODE_CLM_OFFSET_MODE, ExcelHelper.CELL_NORMAL);
			
			xlHelper.setCellValues(xlSheetParams, PARAMS_HEADERS_VALUE, PARAMS_HEADER_NAMES_ROW, modeColumn, ExcelHelper.PARAM_HEADER);
		}
		
		if (isXl2VirSat) {
			writeParamList(xlSheetParams, virsatXlCalculationBean.getFromExcel2VirSat(), isXl2VirSat);
		} else {
			writeParamList(xlSheetParams, virsatXlCalculationBean.getFromVirSat2Excel(), isXl2VirSat);
		}
	}
	
	
	/**
	 * This method crawls the Excel Input and Output Parameters as specified in the Virtual Satellite
	 * data model, and hands back a set of modes used by either the input or output parameters.
	 * @return the sorted set of used modes
	 */
	private SortedSet<SystemMode> getUsedParameterModes() {
		SortedSet<SystemMode> usedSystemModes = new TreeSet<>();
		
		virsatXlCalculationBean.getFromExcel2VirSat().forEach((parameter) -> {
			parameter.getModeValues().forEach((modeValue) -> {
				SystemMode systemMode = modeValue.getMode();
				usedSystemModes.add(systemMode);
			});
		});
		
		virsatXlCalculationBean.getFromVirSat2Excel().forEach((parameter) -> {
			parameter.getModeValues().forEach((modeValue) -> {
				SystemMode systemMode = modeValue.getMode();
				usedSystemModes.add(systemMode);
			});
		});
		
		return usedSystemModes;
	}
	
	/**
	 * Read the output parameters from the excel sheet 
	 * and synchronize them with the data model
	 */
	private void processSheetXl2VirSatParams() {
		RecordingCommand updateCommand = new RecordingCommand(editingDomain) {
			@Override
			protected void doExecute() {
				updateXl2VirSatOutputs();
			}
		};
		
		editingDomain.getCommandStack().execute(updateCommand);
	}
	
	/**
	 * Update the output params with the values in the excel sheet
	 */
	private void updateXl2VirSatOutputs() {
		List<Parameter> parameters = virsatXlCalculationBean.getFromExcel2VirSat();
		
		for (Parameter parameter : parameters) {
			int rowIndex = getParameterRowIndex(parameter);
			boolean hasRights = RightsHelper.hasWritePermission(parameter.getTypeInstance(), userContext);
			if (hasRights) {
				// If there are access rights to the referenced output parameter try to get the value
				Row rowParameter = xlSheetParamsXl2VirSat.getRow(rowIndex);
				Cell cellDefaultValue = rowParameter.getCell(PARAMETER_VALUE_COLUMN);
				if (cellDefaultValue != null && (cellDefaultValue.getCellType() == CellType.NUMERIC || cellDefaultValue.getCellType() == CellType.FORMULA)) {
					double value = cellDefaultValue.getNumericCellValue();
					parameter.setDefaultValue(value);
				}
				
				// Now try the same with all available mode values
				for (Value modeValue : parameter.getModeValues()) {
					SystemMode systemMode = modeValue.getMode();
					String systemModeName = systemMode.getName();
					int modeColumnIndex = getModeColumnIndex(systemModeName);
					Cell cellModeValue = rowParameter.getCell(modeColumnIndex);
					if (cellModeValue != null && (cellModeValue.getCellType() == CellType.NUMERIC || cellModeValue.getCellType() == CellType.FORMULA)) {
						double value = cellModeValue.getNumericCellValue();
						modeValue.setValue(value);
					}
				}
			}
		}
	}
	
	/**
	 * Perform visual improvements to the column formatting
	 * @param xlSheetParams either the input or output sheet 
	 */
	private void prettifySheetParamsColumns(Sheet xlSheetParams) {
		int maxColumns = excelSystemModesColumIndex.values().stream().max(Integer::compare).orElseGet(() -> 0) + MODE_VALUE_CLM_OFFSET_NOTE;
		for (int i = 0; i < maxColumns; ++i) {
			xlSheetParams.autoSizeColumn(i);
		}
	}
	
	/**
	 * Save the write results
	 */
	private void saveExcelFile() {
		URI eResourceUri = virsatXlCalculationBean.getExcelFileBean().getValue();
		URIConverter converter = editingDomain.getResourceSet().getURIConverter();
			
		try {
			OutputStream outputStream = converter.createOutputStream(eResourceUri);
			xlWorkbook.write(outputStream);
			outputStream.close();
			DVLMEditPlugin.getPlugin().getLog().log(new Status(Status.INFO, "Excel Calculation",
					"Successfully updated excel calculation with file " + outputStream.toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Outputs the list of parameters in the given list starting with the passed row.
	 * @param xlSheetParams 
	 * @param parameters the parameters to be written to the params sheet
	 * @param isXl2Virsat whether we are printing an output list (true) or an input list (false)
	 */
	private void writeParamList(Sheet xlSheetParams, IBeanList<Parameter> parameters, boolean isXl2Virsat) {
		Set<Integer> nonUpdatedParamRows = new HashSet<>(parameterUuidRowIndex.values());
		
		for (Parameter parameter : parameters) {
			int row = getParameterRowIndex(parameter);
			nonUpdatedParamRows.remove(row);
			
			xlHelper.setCellValue(xlSheetParams, parameter.getATypeInstance().getUuid().toString(), row, ID_COLUMN, ExcelHelper.CELL_NORMAL);
			xlHelper.setCellValue(xlSheetParams, parameter.getName(), row, PARAMETER_NAME_COLUMN, ExcelHelper.CELL_NORMAL);
			xlHelper.setCellValue(xlSheetParams, PARAMETER_CONNECTED_VALUE_CONNECTED, row, PARAMETER_CONNECTED_COLUMN, ExcelHelper.CELL_NORMAL);
			
			if (!isXl2Virsat) {
				String value = parameter.getDefaultValueBean().isSet() ? String.valueOf(parameter.getDefaultValue()) : NONE;
				xlHelper.setCellValue(xlSheetParams, value, row, PARAMETER_VALUE_COLUMN, ExcelHelper.CELL_NORMAL);
			} else {
				xlHelper.setCellValue(xlSheetParams, null, row, PARAMETER_VALUE_COLUMN, ExcelHelper.CELL_NORMAL);
			}

			AUnit unit = parameter.getDefaultValueBean().getTypeInstance().getUnit();
			xlHelper.setCellValue(xlSheetParams, unit == null ? NONE : unit.getName(), row, PARAMETER_UNIT_COLUMN, ExcelHelper.CELL_NORMAL);
			xlHelper.setCellValue(xlSheetParams, parameter.getNote(), row, PARAMETER_NOTE_COLUMN, ExcelHelper.CELL_NORMAL);
			
			// now write the mode depending values if they exist
			// write values to the ones where we have mode values
			// and write NA to the ones where no mode Values are set in VirSat
			// Create a hashset to remember which modes we wrote for the current parameter
			Set<String> xlSystemModes = new HashSet<>(this.excelSystemModesColumIndex.keySet());
			for (Value modeValue : parameter.getModeValues()) {
				SystemMode parameterSystemMode = modeValue.getMode();
				int modeColumnIndex = getModeColumnIndex(parameterSystemMode.getName());
				
				// Write the Value of the Mode Value
				if (!isXl2Virsat) {
					String value = modeValue.getValueBean().isSet() ? String.valueOf(modeValue.getValue()) : NONE;
					xlHelper.setCellValue(xlSheetParams, value, row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_VALUE, ExcelHelper.CELL_NORMAL);
				} else {
					xlHelper.setCellValue(xlSheetParams, null, row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_VALUE, ExcelHelper.CELL_NORMAL);
				}

				AUnit unitModeValue = modeValue.getValueBean().getTypeInstance().getUnit();
				xlHelper.setCellValue(xlSheetParams, unitModeValue == null ? NONE : unitModeValue.getName(), row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_UNIT, ExcelHelper.CELL_NORMAL);
				xlHelper.setCellValue(xlSheetParams, MODE_VALUE_CLM_OFFSET_VALUE_NA, row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_NOTE, ExcelHelper.CELL_NA);
				
				// We wrote the mode value, so take it out of this list
				xlSystemModes.remove(parameterSystemMode.getName());
			}

			// The remaining modes are the ones we mark with no mode
			for (String naSystemMode : xlSystemModes) {
				int modeColumnIndex = excelSystemModesColumIndex.get(naSystemMode);
				xlHelper.setCellValue(xlSheetParams, MODE_VALUE_CLM_OFFSET_VALUE_NA, row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_VALUE, ExcelHelper.CELL_NA);
				xlHelper.setCellValue(xlSheetParams, MODE_VALUE_CLM_OFFSET_VALUE_NA, row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_UNIT, ExcelHelper.CELL_NA);
				xlHelper.setCellValue(xlSheetParams, MODE_VALUE_CLM_OFFSET_VALUE_NA, row, modeColumnIndex + MODE_VALUE_CLM_OFFSET_NOTE, ExcelHelper.CELL_NA);
			}
		}
		
		// Now mark all the Parameter Rows that have not been visited as disconnected
		for (Integer nonUpdatedParamRow : nonUpdatedParamRows) {
			xlHelper.setCellValue(xlSheetParams, PARAMETER_CONNECTED_VALUE_DISCONNECTED, nonUpdatedParamRow, PARAMETER_CONNECTED_COLUMN, ExcelHelper.CELL_NORMAL);
		}
	}
}
