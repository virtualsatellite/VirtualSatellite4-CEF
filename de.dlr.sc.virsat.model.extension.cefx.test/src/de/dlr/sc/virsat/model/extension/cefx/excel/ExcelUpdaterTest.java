/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dlr.sc.virsat.concept.unittest.util.test.AConceptProjectTestCase;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.roles.UserRegistry;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.ExcelCalculation;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.resources.VirSatResourceSet;
import de.dlr.sc.virsat.project.structure.VirSatProjectCommons;

/**
 * Class testing the ExcelUpdater for ExcelCalculations
 * @author muel_s8
 *
 */

public class ExcelUpdaterTest extends AConceptProjectTestCase {

	protected static final double TEST_EPSILON =  0.0001;
	
	private static final String CONCEPT_PS = de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId();
	private static final String CONCEPT_CEFX = de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID;
	
	private static final String FRAGMENT_ID = "de.dlr.sc.virsat.model.extension.cefx.test";
	
	private static final String EXCEL_FILE_UNPREPARED = "VirSatExcelCalculationUnprepared.xlsx";
	private static final String EXCEL_FILE_PREPARED = "VirSatExcelCalculationPreparedUpdate.xls";
	
	private URI excelFileUri;
	
	private Concept conceptPS;
	private Concept conceptCEFX;
	private StructuralElementInstance sei;
	private Parameter paramInput;
	private Parameter paramOutput;
	
	private SystemMode modeA;
	private SystemMode modeB;
	
	private Value modeValueA;
	private Value modeValueB;
	
	private ExcelCalculation excelCalc;
	
	private VirSatResourceSet rs;
	private VirSatProjectCommons projectCommons;
	
	@Before
	public void setUp() throws CoreException {
		super.setUp();
		addEditingDomainAndRepository();
		UserRegistry.getInstance().setSuperUser(true);
		
	    projectCommons = new VirSatProjectCommons(testProject);
		projectCommons.createProjectStructure(null);
		rs = editingDomain.getResourceSet();
		
		conceptPS = loadConceptFromPlugin(CONCEPT_PS);
		conceptCEFX = loadConceptFromPlugin(CONCEPT_CEFX);
		
	    ElementConfiguration equipment = new ElementConfiguration(conceptPS);
	    sei = equipment.getStructuralElementInstance();
		
		paramInput = new Parameter(conceptCEFX);
		paramOutput = new Parameter(conceptCEFX);
		
		modeA = new SystemMode(conceptCEFX);
		modeB = new SystemMode(conceptCEFX);
		
		modeA.setName("Mode A");
		modeB.setName("Mode B");
		
		modeValueA = new Value(conceptCEFX);
		modeValueB = new Value(conceptCEFX);
		
		modeValueA.setMode(modeA);
		modeValueB.setMode(modeB);
		
		paramInput.getModeValues().add(modeValueA);
		paramOutput.getModeValues().add(modeValueB);
		
		excelCalc = new ExcelCalculation(conceptCEFX);
		equipment.add(excelCalc);
		
		excelCalc.getFromVirSat2Excel().add(paramInput);
		excelCalc.getFromExcel2VirSat().add(paramOutput);
		
		RecordingCommand setup = new RecordingCommand(editingDomain) {
			@Override
			protected void doExecute() {
				projectCommons.createFolderStructure(sei, null);
				rs.initializeStructuralElementInstance(sei);
				repository.getRootEntities().add(sei);
			}
	    };

	    editingDomain.getCommandStack().execute(setup);
		editingDomain.saveAll();
	}
	
	/**
	 * Create a command that sets up the data model for the test
	 * and that loads and connects the given excel sheet with this data model
	 * @param fileName the name of the excelsheet in the test resources
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws CoreException 
	 */
	public void setupExcelFile(String fileName) throws MalformedURLException, IOException, CoreException {
	    // get our excel file in the plugin
		IFolder documentFolder = VirSatProjectCommons.getDocumentFolder(sei); 
		URL url = FileLocator.toFileURL(new URL("platform:/plugin/" + FRAGMENT_ID + "/resources/" + fileName));
		
		// copy the file into the workspace
		IFile file = documentFolder.getFile(fileName);
		InputStream stream = url.openStream();
		file.create(stream, true, null);
		stream.close();
		
		// get its new URI
		String copiedFilePath = file.getFullPath().toOSString();
		excelFileUri = URI.createPlatformResourceURI(copiedFilePath, false);
		editingDomain.getCommandStack().execute(excelCalc.setExcelFile(editingDomain, excelFileUri));
	}
	
	@After
	public void tearDown() throws CoreException {
		super.tearDown();
		UserRegistry.getInstance().setSuperUser(false);
	}
	
	@Test
	public void testPerformUpdate() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_PREPARED);
		
		//CHECKSTYLE:OFF

		paramInput.setDefaultValue(300);
		modeValueA.setValue(3);
	
		ExcelUpdater.performUpdate(sei, UserRegistry.getInstance());

		// do some further layouting checks on the Mode Values
		ExcelHelper xlHelper = getExcelHelperForTestResult();
		Sheet sheetVirSat2Xl = wb.getSheet(ExcelUpdater.PARAMS_VIRSAT_2_XL_SHEET_NAME);
		Sheet sheetXl2VirSat = wb.getSheet(ExcelUpdater.PARAMS_XL_2_VIRSAT_SHEET_NAME);

		assertEquals("Mode Headers have been succesfully writen", "Mode A", xlHelper.getCellStringValue(sheetVirSat2Xl, 3, 7));
		assertEquals("Mode Headers have been succesfully writen", "Mode X", xlHelper.getCellStringValue(sheetVirSat2Xl, 3, 10));
		assertEquals("Mode Headers have been succesfully writen", "Mode B", xlHelper.getCellStringValue(sheetVirSat2Xl, 3, 13));
		
		assertEquals("Mode Headers have been succesfully writen", "Mode A", xlHelper.getCellStringValue(sheetXl2VirSat, 3, 7));
		assertEquals("Mode Headers have been succesfully writen", "Mode X", xlHelper.getCellStringValue(sheetXl2VirSat, 3, 10));
		assertEquals("Mode Headers have been succesfully writen", "Mode B", xlHelper.getCellStringValue(sheetXl2VirSat, 3, 13));

		assertEquals("Parameter Disconnected", ExcelUpdater.PARAMETER_CONNECTED_VALUE_DISCONNECTED, xlHelper.getCellStringValue(sheetVirSat2Xl, 5, 2));
		assertEquals("Parameter Connected", ExcelUpdater.PARAMETER_CONNECTED_VALUE_CONNECTED, xlHelper.getCellStringValue(sheetVirSat2Xl, 6, 2));
		
		assertEquals("Mode Values have been succesfully writen", 300.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 6, 3), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen",   3.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 6, 6), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen", "N/A", xlHelper.getCellStringValue(sheetVirSat2Xl, 6, 9));
		assertEquals("Mode Values have been succesfully writen", "N/A", xlHelper.getCellStringValue(sheetVirSat2Xl, 6, 12));

		assertEquals("Parameter Disconnected", ExcelUpdater.PARAMETER_CONNECTED_VALUE_DISCONNECTED, xlHelper.getCellStringValue(sheetXl2VirSat, 5, 2));
		assertEquals("Parameter Connected", ExcelUpdater.PARAMETER_CONNECTED_VALUE_CONNECTED, xlHelper.getCellStringValue(sheetXl2VirSat, 6, 2));
		
		assertEquals("Mode Values have been succesfully writen", 500.0, xlHelper.getCellNumericValue(sheetXl2VirSat, 6, 3), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen", "N/A", xlHelper.getCellStringValue(sheetXl2VirSat, 6, 6));
		assertEquals("Mode Values have been succesfully writen", "N/A", xlHelper.getCellStringValue(sheetXl2VirSat, 6, 9));
		assertEquals("Mode Values have been succesfully writen",   5.0, xlHelper.getCellNumericValue(sheetXl2VirSat, 6, 12), TEST_EPSILON);
		
		// Check that the output is correctly updated after changing the input
		assertEquals("Update successful", 500.0, paramOutput.getDefaultValue(), TEST_EPSILON);
		assertEquals("Update successful",   5.0, modeValueB.getValue(), TEST_EPSILON);

		
		// now perform an update of what we have already calculated
		paramInput.setDefaultValue(0);
		modeValueA.setValue(0);

		ExcelUpdater.performUpdate(sei, UserRegistry.getInstance());

		// Check that the output is correctly updated after changing the input
		assertEquals("Update successful", 400.0, paramOutput.getDefaultValue(), TEST_EPSILON);
		assertEquals("Update successful",   4.0, modeValueB.getValue(), TEST_EPSILON);

		//CHECKSTYLE:ON
	}

	@Test
	public void testPerformUpdateUnprepared() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		//CHECKSTYLE:OFF
		paramInput.setDefaultValue(345);
		modeValueA.setValue(34);
	
		ExcelUpdater.performUpdate(sei, UserRegistry.getInstance());

		// do some further layouting checks on the Mode Values
		ExcelHelper xlHelper = getExcelHelperForTestResult();
		Sheet sheetVirSat2Xl = wb.getSheet(ExcelUpdater.PARAMS_VIRSAT_2_XL_SHEET_NAME);
		Sheet sheetXl2VirSat = wb.getSheet(ExcelUpdater.PARAMS_XL_2_VIRSAT_SHEET_NAME);

		assertEquals("Mode Headers have been succesfully writen", "Mode A", xlHelper.getCellStringValue(sheetVirSat2Xl, 3, 7));
		assertEquals("Mode Headers have been succesfully writen", "Mode B", xlHelper.getCellStringValue(sheetVirSat2Xl, 3, 10));
		
		assertEquals("Mode Headers have been succesfully writen", "Mode A", xlHelper.getCellStringValue(sheetXl2VirSat, 3, 7));
		assertEquals("Mode Headers have been succesfully writen", "Mode B", xlHelper.getCellStringValue(sheetXl2VirSat, 3, 10));
		
		assertEquals("Mode Values have been succesfully writen", 345.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 5, 3), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen",  34.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 5, 6), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen", "N/A", xlHelper.getCellStringValue(sheetVirSat2Xl, 5, 9));
		
		//CHECKSTYLE:ON
	}
	
	@Test
	public void testPerformUpdateUnpreparedNoModes() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		//CHECKSTYLE:OFF
		paramInput.setDefaultValue(345);
		paramInput.getModeValues().clear();
		paramOutput.getModeValues().clear();
	
		ExcelUpdater.performUpdate(sei, UserRegistry.getInstance());

		// do some further layouting checks on the Mode Values
		ExcelHelper xlHelper = getExcelHelperForTestResult();
		Sheet sheetVirSat2Xl = wb.getSheet(ExcelUpdater.PARAMS_VIRSAT_2_XL_SHEET_NAME);
		
		assertEquals("Mode Values have been succesfully writen", 345.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 5, 3), TEST_EPSILON);
		
		//CHECKSTYLE:ON
	}
	
	@Test
	public void testPerformUpdateUnpreparedNoInputs() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		//CHECKSTYLE:OFF
		Command cmd = excelCalc.getFromVirSat2Excel().remove(editingDomain, paramInput);
		editingDomain.getCommandStack().execute(cmd);
		ExcelUpdater.performUpdate(sei, UserRegistry.getInstance());

		// do some further layouting checks on the Mode Values
		ExcelHelper xlHelper = getExcelHelperForTestResult();
		Sheet sheetXl2VirSat = wb.getSheet(ExcelUpdater.PARAMS_XL_2_VIRSAT_SHEET_NAME);
		
		assertEquals("Mode Headers have been succesfully writen", "Mode B", xlHelper.getCellStringValue(sheetXl2VirSat, 3, 7));
		//CHECKSTYLE:ON
	}

	@Test
	public void testPerformUpdateUnpreparedNoOutputs() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		//CHECKSTYLE:OFF
		paramInput.setDefaultValue(345);
		modeValueA.setValue(34);
		Command cmd = excelCalc.getFromVirSat2Excel().remove(editingDomain, paramOutput);
		editingDomain.getCommandStack().execute(cmd);
		ExcelUpdater.performUpdate(sei, UserRegistry.getInstance());

		// do some further layouting checks on the Mode Values
		ExcelHelper xlHelper = getExcelHelperForTestResult();
		Sheet sheetVirSat2Xl = wb.getSheet(ExcelUpdater.PARAMS_VIRSAT_2_XL_SHEET_NAME);

		assertEquals("Mode Headers have been succesfully writen", "Mode A", xlHelper.getCellStringValue(sheetVirSat2Xl, 3, 7));
		
		assertEquals("Mode Values have been succesfully writen", 345.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 5, 3), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen",  34.0, xlHelper.getCellNumericValue(sheetVirSat2Xl, 5, 6), TEST_EPSILON);
		assertEquals("Mode Values have been succesfully writen", "N/A", xlHelper.getCellStringValue(sheetVirSat2Xl, 5, 9));
		//CHECKSTYLE:ON
	}
	
	@Test
	public void testPerformUpdateNoExcel() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		Command setCommand = excelCalc.setExcelFile(editingDomain, null);
		editingDomain.getCommandStack().execute(setCommand);
		ExcelUpdater updater = new ExcelUpdater(testProject, editingDomain, excelCalc, UserRegistry.getInstance()).openExcelFile();
		assertFalse("Excel Updater cannot udpater", updater.canUpdateExcelFile());
	}
	
	@Test
	public void testPerformUpdateUnpreparedMissingInputMode() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		paramInput.getModeValues().get(0).setMode(new SystemMode());
		
		ExcelUpdater updater = new ExcelUpdater(testProject, editingDomain, excelCalc, UserRegistry.getInstance()).openExcelFile();
		assertFalse("Excel Updater cannot udpater", updater.canUpdateExcelFile());
	}
	
	@Test
	public void testPerformUpdateUnpreparedMissingInputParameter() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		Command cmd = excelCalc.getFromVirSat2Excel().remove(editingDomain, paramInput);
		editingDomain.getCommandStack().execute(cmd);
		cmd = excelCalc.getFromVirSat2Excel().add(editingDomain, new Parameter());
		editingDomain.getCommandStack().execute(cmd);
		
		ExcelUpdater updater = new ExcelUpdater(testProject, editingDomain, excelCalc, UserRegistry.getInstance()).openExcelFile();
		assertFalse("Excel Updater cannot udpater", updater.canUpdateExcelFile());
	}
	
	@Test
	public void testPerformUpdateUnpreparedMissingOutputMode() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		paramOutput.getModeValues().get(0).setMode(new SystemMode());
		
		ExcelUpdater updater = new ExcelUpdater(testProject, editingDomain, excelCalc, UserRegistry.getInstance()).openExcelFile();
		assertFalse("Excel Updater cannot udpater", updater.canUpdateExcelFile());
	}
	
	@Test
	public void testPerformUpdateUnpreparedMissingOutputParameter() throws MalformedURLException, IOException, CoreException {
		setupExcelFile(EXCEL_FILE_UNPREPARED);
		
		Command cmd = excelCalc.getFromExcel2VirSat().remove(editingDomain, paramOutput);
		editingDomain.getCommandStack().execute(cmd);
		cmd = excelCalc.getFromExcel2VirSat().add(editingDomain, new Parameter());
		editingDomain.getCommandStack().execute(cmd);
		
		ExcelUpdater updater = new ExcelUpdater(testProject, editingDomain, excelCalc, UserRegistry.getInstance()).openExcelFile();
		assertFalse("Excel Updater cannot udpater", updater.canUpdateExcelFile());
	}
	
	private Workbook wb;
	
	/**
	 * Just a Helper method that gives access to the currently written EXCEL file
	 * to check what was actually written in the overall test case
	 * @return the ExcelHelper giving access to the currently written Excel
	 * @throws IOException 
	 */
	private ExcelHelper getExcelHelperForTestResult() throws IOException {
		URI eResourceUri = excelFileUri;
		URIConverter converter = editingDomain.getResourceSet().getURIConverter();
		
		InputStream inputStream = null;
		
		try {
			inputStream = converter.createInputStream(eResourceUri);
			wb = new HSSFWorkbook(inputStream);
		} catch (OfficeXmlFileException e) {
			inputStream = converter.createInputStream(eResourceUri);
			wb = new XSSFWorkbook(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		
		return new ExcelHelper(wb);
	}
}
