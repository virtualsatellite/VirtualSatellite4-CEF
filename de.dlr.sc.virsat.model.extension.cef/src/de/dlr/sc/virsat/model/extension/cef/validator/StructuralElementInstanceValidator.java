/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.validator;

import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ReferencePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ResourcePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.UnitValuePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.general.IUuid;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.ExcelCalculation;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.Value;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;

import de.dlr.sc.virsat.build.marker.util.VirSatValidationMarkerHelper;
import de.dlr.sc.virsat.model.dvlm.validator.IStructuralElementInstanceValidator;


// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Class inheriting from Generator Gap Class
 * 
 * This class is generated once, do your changes here
 * 
 * VirSat DLR CEF Concept
 * 
 */
public class StructuralElementInstanceValidator extends AStructuralElementInstanceValidator implements IStructuralElementInstanceValidator {

	private BeanCategoryAssignmentHelper bCaHelper = new BeanCategoryAssignmentHelper();
	private VirSatValidationMarkerHelper vvmHelper = new VirSatValidationMarkerHelper();
	
	public static final String WARNING_MISSING_EXCEL_FILE = "Excel file is not set correctly for Excel Calculation ";
	public static final String WARNING_MISSING_DEFAULT_VALUE = "Default value of Parameter is not set ";
	public static final String WARNING_MISSING_MODE_VALUE_VALUE = "Value of Mode Value is not set ";
	public static final String WARNING_MISSING_MODE_VALUE_MODE = "Mode of Mode Value is not set ";
	public static final String WARNING_DEFAULT_VALUE_NOT_NUMERIC = "Default Value of Parameter is not numeric ";
	public static final String WARNING_MODE_VALUE_NOT_NUMERIC = "Mode Value of Parameter is not numeric ";
	public static final String WARNING_MODE_VALUE_ASSIGNED_MULTIPLE = "Multiple Mode Values assigned to the same system mode ";
	public static final String WARNING_DEFAULT_VALUE_OUT_OF_RANGE = "Default Value of Parameter is out of range ";
	
	@Override
	public boolean validate(StructuralElementInstance sei) {
		boolean allInfoValid = true;
		
		allInfoValid &= validateParameters(sei);
		allInfoValid &= validateExcelCalculations(sei);
		
		// now check if there are InterfaceEnds which are used more than once
		return super.validate(sei) && allInfoValid;
	}
	
	/**
	 * Validates the correctness of the Parameters attached to the given sei
	 * @param sei the sei to validate
	 * @return true iff all parameter data is correct
	 */
	private boolean validateParameters(StructuralElementInstance sei) {
		boolean allInfoValid = true;
		
		List<Parameter> parameters = bCaHelper.getAllNestedBeanCategories(sei, Parameter.class);
		for (Parameter parameter : parameters) {
			// check if the modes and values are all set for this parameter
			String fqn = parameter.getTypeInstance().getFullQualifiedInstanceName();
			UnitValuePropertyInstance uvpi = parameter.getDefaultValueBean().getTypeInstance();
			IUuid iUuid = uvpi;	
			
			allInfoValid &= validateParameterRange(parameter, fqn, iUuid);
			allInfoValid &= validateParameterDefaultValue(parameter, fqn, iUuid);
			allInfoValid &= validateParameterModeValues(parameter, fqn);
		}

		return allInfoValid;
	}
	
	/**
	 * Check if range values are specified for the parameter
	 * @param parameter the parameter bean
	 * @param fqn the full qualified name of the parameter
	 * @param iUuid the uuid of the parameter instance
	 * @return true iff parameter values are in range
	 */
	private boolean validateParameterRange(Parameter parameter, String fqn, IUuid iUuid) {
		boolean allInfoValid = true;
		if (!parameter.getRangeValues().isEmpty()) {
			if (parameter.getRangeValues().get(0).isSetMinValue()) {
				if (parameter.getDefaultValue() < parameter.getRangeValues().get(0).getMinValue()) {
					allInfoValid = false;
					vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_DEFAULT_VALUE_OUT_OF_RANGE + fqn, iUuid);
				}
			}
			if (parameter.getRangeValues().get(0).isSetMaxValue()) {
				if (parameter.getDefaultValue() > parameter.getRangeValues().get(0).getMaxValue()) {
					allInfoValid = false;
					vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_DEFAULT_VALUE_OUT_OF_RANGE + fqn, iUuid);
				}
			}
		}	
		
		return allInfoValid;
	}
	
	/**
	 * Check if the default value is correctly set
	 * @param parameter the parameter bean
	 * @param fqn the full qualified name of the parameter
	 * @param iUuid the uuid of the parameter instance
	 * @return true iff parameter values are in range
	 */
	private boolean validateParameterDefaultValue(Parameter parameter, String fqn, IUuid iUuid) {
		boolean allInfoValid = true;
		
		if (!parameter.isSetDefaultValue()) {
			allInfoValid = false;													
			vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_MISSING_DEFAULT_VALUE + fqn, iUuid);
		} else {
			boolean parameterOk = false;
			
			try {
				double value = parameter.getDefaultValue();
				parameterOk = !Double.isNaN(value);
			} catch (Exception e) {
			}
			
			if (!parameterOk) {
				allInfoValid = false;
				vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_DEFAULT_VALUE_NOT_NUMERIC + fqn, iUuid);
			}
		}
		
		return allInfoValid;
	}
	
	/**
	 * Check if the mode values are set correctly
	 * @param parameter the parameter bean
	 * @param fqn the full qualified name of the parameter
	 * @return true iff parameter values are in range
	 */
	private boolean validateParameterModeValues(Parameter parameter, String fqn) {
		boolean allInfoValid = true;
		
		List<SystemMode> assignedSystemModes = new ArrayList<SystemMode>();
		for (Value modeValue : parameter.getModeValues()) {
			UnitValuePropertyInstance uvpi = modeValue.getValueBean().getTypeInstance();
			IUuid iUuid = uvpi;
			
			if (!modeValue.isSetValue()) {
				allInfoValid = false;															
				vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_MISSING_MODE_VALUE_VALUE + fqn, iUuid);
			} else {
				boolean modeValueOk = false;
				
				try {
					double value = parameter.getDefaultValue();
					modeValueOk = !Double.isNaN(value);
				} catch (Exception e) {
				}
				
				if (!modeValueOk) {
					allInfoValid = false;														
					vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_MODE_VALUE_NOT_NUMERIC + fqn, iUuid);
				}
			}

			if (modeValue.getMode() != null) {
				assignedSystemModes.add(modeValue.getMode());
			}	

			if (modeValue.getMode() == null) {
				allInfoValid = false;
				ReferencePropertyInstance rpi = modeValue.getTypeReferenceProperty();
				iUuid = rpi;																	
				vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_MISSING_MODE_VALUE_MODE + fqn, iUuid);
			}
		}
		
		
		//validator to check if multiple mode values are assigned to one system mode
		
		final Set<SystemMode> setOfAllModeValues = new HashSet<SystemMode>();
		final Set<SystemMode> setOfDuplicateModeAssignment = new HashSet<SystemMode>();
		
		for (SystemMode sm : assignedSystemModes) {
			if (!setOfAllModeValues.add(sm)) {
				setOfDuplicateModeAssignment.add(sm);
			}
		}
		 
		if (!setOfDuplicateModeAssignment.isEmpty()) {
			for (SystemMode sm : setOfDuplicateModeAssignment) {
				for (Value md: parameter.getModeValues()) {
					if (md.getMode() != null && md.getMode().equals(sm)) {
						ReferencePropertyInstance iUuid = md.getTypeReferenceProperty();
						vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_MODE_VALUE_ASSIGNED_MULTIPLE + fqn, iUuid);
					}	
				}
			}	
		}
		
		return allInfoValid;
	}
	
	/**
	 * Validates the correctness of the ExcelCaluldations attached to the given sei
	 * @param sei the sei to validate
	 * @return true iff all excel calulation data is correct
	 */
	private boolean validateExcelCalculations(StructuralElementInstance sei) {
		boolean allInfoValid = true;
		
		List<ExcelCalculation> excelCalcs = bCaHelper.getAllNestedBeanCategories(sei, ExcelCalculation.class);
		for (ExcelCalculation excelCalc : excelCalcs) {
			// check if an excel file has been attached and if all parameters (input/output) have been assigned
			String fqn = excelCalc.getTypeInstance().getFullQualifiedInstanceName();
			boolean excelOk = false;
			try {
				excelOk = excelCalc.getExcelFile() != null;
			} catch (Exception e) {
			}
			
			if (!excelOk) {
				allInfoValid = false;
				ResourcePropertyInstance rpi = excelCalc.getExcelFileBean().getTypeInstance();
				IUuid iUuid = rpi;																	
				vvmHelper.createDVLMValidationMarker(IMarker.SEVERITY_WARNING, WARNING_MISSING_EXCEL_FILE + fqn, iUuid);
			}
		}
		
		return allInfoValid;
	}
}
