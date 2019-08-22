/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import de.dlr.sc.virsat.model.concept.types.structural.ABeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.BeanStructuralElementInstance;
import de.dlr.sc.virsat.model.concept.types.structural.IBeanStructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.util.StructuralElementInstanceHelper;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cefx.model.Value;

/**
 * The mode helper class is used to get all system modes for a given parameter
 * and to hand back the ones which are currently active. Additionally this class provides functionality
 * to either add or a remove a mode value by using the EMF Commands
 * @author fisc_ph
 *
 */
public class CefModeHelper {

	public static final SystemMode DEFAULT_MODE = null;

	/**
	 * This method hands back all System Modes of a given parameter
	 * @param param the parameter for which to get all the modes
	 * @return a List of all system modes
	 */
	public List<SystemMode> getAllModes(Parameter param) {
		// get containing bean ro get the root and then to search all system modes in the tree
		IBeanStructuralElementInstance beanSei = param.getParent();
		StructuralElementInstance rootSei = (StructuralElementInstance) new StructuralElementInstanceHelper(beanSei.getStructuralElementInstance()).getRoot();
		IBeanStructuralElementInstance rootBeanSei = new BeanStructuralElementInstance(rootSei);

		List<SystemMode> systemModes = rootBeanSei.getAll(SystemMode.class);
		
		rootBeanSei.getDeepChildren(ABeanStructuralElementInstance.class).forEach((childBeanSei) -> systemModes.addAll(childBeanSei.getAll(SystemMode.class)));
		return systemModes;
	}
	
	/**
	 * Hands back a list of all modes which are used by mode values of this parameter
	 * @param param the parameter for which to get the modes
	 * @return a list of all currently used modes
	 */
	public List<SystemMode> getAllActiveModes(Parameter param) {
		List<SystemMode> systemModes = new ArrayList<>();
		param.getModeValues().forEach((modeValue) -> {
			systemModes.add(modeValue.getMode());
		});
		return systemModes;
	}
	
	/**
	 * This method creates a command to add a mode value to the parameter
	 * @param ed the editing domain to be used to create the mode value
	 * @param parameter the parameter to which to add the mode value
	 * @param systemMode the system mode to be used for the mode value
	 * @return the command that will add the new mode value
	 */
	public Command addModeValue(EditingDomain ed, Parameter parameter, SystemMode systemMode) {
		Concept concept = parameter.getConcept();
		Value value = new Value(concept);
		value.setMode(systemMode);
		
		AUnit defaultValueUnit = parameter.getDefaultValueBean().getTypeInstance().getUnit();
		value.getValueBean().getTypeInstance().setUnit(defaultValueUnit);
		
		return parameter.getModeValues().add(ed, value);
	}
	
	/**
	 * Gets the mode value of a parameter explicitly defined for a system mode
	 * @param parameter the parameter
	 * @param systemMode the system mode
	 * @return the explicitly defined mode value of the parameter in the given system mode,
	 * null otherwise
	 */
	public Value getModeValue(Parameter parameter, SystemMode systemMode) {
		for (Value modeValue : parameter.getModeValues()) {
			if (systemMode.equals(modeValue.getMode())) {
				return modeValue;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the value of a parameter in a specific mode.
	 * If no value is explicitly defined for this mode,
	 * it returns the default value
	 * @param parameter the parameter
	 * @param systemMode the system mode
	 * @return the mode value in the given mode or the default value if no mode is defined
	 */
	public double getModeValueOrDefault(Parameter parameter, SystemMode systemMode) {
		double value = parameter.isSetDefaultValue() 
				?	parameter.getDefaultValue()
				:	0;
		
		if (systemMode != DEFAULT_MODE) {
			CefModeHelper cefModeHelper = new CefModeHelper();
			Value modeValue = cefModeHelper.getModeValue(parameter, systemMode);
			if (modeValue != null) {
				value = modeValue.getValue();
			}
		} 
		
		return value;
	}
	
	/**
	 * This method creates a command to remove a mode value
	 * @param ed the editing domain to be used to create the command
	 * @param parameter the parameter from which to remove the mode value
	 * @param systemMode the system mode for which the mode value should be removed
	 * @return the command that will remove the mode value
	 */
	public Command removeModeValue(EditingDomain ed, Parameter parameter, SystemMode systemMode) {
		Value value = null;
		
		for (Value modeValue : parameter.getModeValues()) {
			if (modeValue.getMode().equals(systemMode)) {
				value = modeValue;
				break;
			}
		}
	
		return parameter.getModeValues().remove(ed, value);
	}
}
