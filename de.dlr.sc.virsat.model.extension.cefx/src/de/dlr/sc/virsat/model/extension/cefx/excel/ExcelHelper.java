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

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Helper class for performing operations on a workbook such as setting a value at some
 * coordinate (x,y)
 * @author muel_s8
 *
 */

public class ExcelHelper {
	
	public static final String SC_HEADER = "sc_header";
	public static final String CELL_NORMAL = "cell_normal";
	public static final String CELL_NA = "cell_na";
	public static final String PARAM_HEADER = "param_header";
	
	private Workbook wb;
	private CreationHelper createHelper;
	private Map<String, CellStyle> styles;
	
	/**
	 * Default constructor for creation, creates all internal sub helpers.
	 * @param wb the workbook to use
	 */
	
	public ExcelHelper(Workbook wb) {
		this.wb = wb;
		
		createHelper = wb.getCreationHelper();
		styles = createStyles();
	}
	
	/**
	 * this method fill the cell and the set the cell style
	 * @param sheet the sheet on which the value will be set at
	 * @param cellValue the value of the cell
	 * @param rowCounter the row index
	 * @param cellCounter the cell index
	 * @param cellStyle the style to use for this cell
	 */
	public void setCellValue(Sheet sheet, String cellValue, int rowCounter, int cellCounter, String cellStyle) {
		Row row;
		if (sheet.getRow(rowCounter) == null) {
			row = sheet.createRow(rowCounter);
		} else {
			row = sheet.getRow(rowCounter);
		}
			
		Cell cell = row.getCell(cellCounter) == null ? row.createCell(cellCounter) : row.getCell(cellCounter);
		if (cellValue != null) {
			if (cellValue.matches("-?\\d+(\\.\\d+)?")) {
				cell.setCellValue(Double.valueOf(cellValue));
			} else {
				cell.setCellValue(createHelper.createRichTextString(cellValue));
			}
		}
		
		if (cellStyle != null) {
			cell.setCellStyle(styles.get(cellStyle));
		}
	}
	
	/**
	 * This method extract a numeric value from a given sheet
	 * @param sheet the sheet from which to get the value
	 * @param rowCounter the rowIndex of the cell
	 * @param cellCounter the coulmnIndex to the Cell
	 * @return The numeric Value in case it exists
	 */
	public double getCellNumericValue(Sheet sheet, int rowCounter, int cellCounter) {
		Cell cell = sheet.getRow(rowCounter).getCell(cellCounter);
		return cell.getNumericCellValue();
	}
	
	/**
	 * This method extract a string value from a given sheet
	 * @param sheet the sheet from which to get the value
	 * @param rowCounter the rowIndex of the cell
	 * @param cellCounter the coulmnIndex to the Cell
	 * @return The string Value in case it exists
	 */
	public String getCellStringValue(Sheet sheet, int rowCounter, int cellCounter) {
		Cell cell = sheet.getRow(rowCounter).getCell(cellCounter);
		return cell.getStringCellValue();
	}
	
	/**
	 * Method to set a whole array of values into a given sheet
	 * @param sheet the sheet in which to write to
	 * @param cellValues the Values to be written
	 * @param rowCounter the rowIndex where to write to
	 * @param cellCounter the columnIndex where to start writing at
	 * @param cellStyle the style to be used for the cells.
	 */
	public void setCellValues(Sheet sheet, String[] cellValues, int rowCounter, int cellCounter, String cellStyle) {
		for (int i = 0; i < cellValues.length; ++i) {
			setCellValue(sheet, cellValues[i], rowCounter, i + cellCounter, cellStyle);
		}
	}
	
	/**
	 * create a library of cell styles
	 * @return a library of cell styles
	 */
	private Map<String, CellStyle> createStyles() {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setBold(true);
		style = createBorderedStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put(SC_HEADER, style);

		style = createBorderedStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put(PARAM_HEADER, style);
		
		style = createBorderedStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put(CELL_NA, style);

		style = createBorderedStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setWrapText(false);
		styles.put(CELL_NORMAL, style);

		return styles;
	}
	
	/**
	 * Create a style with borders
	 * @return a cell style with borders
	 */
	private CellStyle createBorderedStyle() {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}
