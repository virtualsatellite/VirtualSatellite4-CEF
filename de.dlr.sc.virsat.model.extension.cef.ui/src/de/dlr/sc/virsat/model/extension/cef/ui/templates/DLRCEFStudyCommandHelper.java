/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.templates;


import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.IAssignedDiscipline;
import de.dlr.sc.virsat.model.dvlm.roles.Discipline;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.command.CreateAddStructuralElementInstanceCommand;
import de.dlr.sc.virsat.model.extension.cef.Activator;
import de.dlr.sc.virsat.model.extension.cef.model.Equipment;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentTemperatureParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystem;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemPowerParameters;
import de.dlr.sc.virsat.model.extension.cef.model.System;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SystemPowerParameters;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.resources.command.CreateSeiResourceAndFileCommand;
/**
 * Set the parameter for the Concepts (System, SubSystem, Equipment)
 * @author kuja_tj
 *
 */
public class DLRCEFStudyCommandHelper {
	
	public static final String POWER_PARAMETERS = "PowerParameters";
	public static final String MASS_PARAMETERS = "MassParameters";
	public static final String SYSTEM_PARAMETERS = "SystemParameters";
	public static final String EQUIPMENT_MASS_PARAMETERS = "EquipmentMassParameters";
	public static final String TEMPERATURE_PARAMETERS = "TemperatureParameters";
	public static final String SYSTEM_NAME = "System";
	public static final String SUB_SYSTEM_NAME = "SubSystem";
	public static final String EQUIPMENT_NAME = "Equipment";
	
	/**
	 * private Constructor
	 */
	private DLRCEFStudyCommandHelper() {
	}
	/**
	 * Get the cef concept from the domain
	 * @param domain the domain
	 * @return the cef concept
	 */
	public static Concept getCefConcept(VirSatTransactionalEditingDomain domain) {
		Repository currentRepository = domain.getResourceSet().getRepository();
		ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
		Concept activeConcept = acHelper.getConcept(Activator.PLUGIN_ID);
		return activeConcept;
	}

	/**
	 * @param concept the cef concept
	 * @return new system bean
	 */
	public static System createSystemBean(Concept concept) {
		System system = new System(concept);
		system.setName(SYSTEM_NAME);
		return system;
	}
	
	/**
	 * @param concept the cef concept
	 * @return new subSystem bean
	 */
	public static SubSystem createSubSystemBean(Concept concept) {
		SubSystem subSystem = new SubSystem(concept);
		subSystem.setName(SUB_SYSTEM_NAME);
		return subSystem;
	}

	/**
	 * @param concept the cef concept
	 * @return new equipment bean
	 */
	public static Equipment createEquipmentBean(Concept concept) {
		Equipment equipment = new Equipment(concept);
		equipment.setName(EQUIPMENT_NAME);
		return equipment;
	}

	//CHECKSTYLE:OFF
	public static void addSystemParameters(Concept concept, System system) {
		//default unit in concept is percent 
		SystemParameters systemParameters = new SystemParameters(concept);
		systemParameters.setName(SYSTEM_PARAMETERS);
		systemParameters.setSystemMargin(20);  
		system.add(systemParameters);
		
		SystemMassParameters systemMassParameters = new SystemMassParameters(concept);
		systemMassParameters.setName(MASS_PARAMETERS);
		systemMassParameters.getMassAdapter().setDefaultValue(100);
		systemMassParameters.getMassLaunchMax().setDefaultValue(850);
		systemMassParameters.getMassPropellant().setDefaultValue(35);
		system.add(systemMassParameters);
		
		SystemPowerParameters systemPowerParameters = new SystemPowerParameters(concept);
		systemPowerParameters.setName(POWER_PARAMETERS);
		system.add(systemPowerParameters);
	}

	public static void addSubSystemParameters(Concept concept, SubSystem subSystem) {
		SubSystemMassParameters subSystemMassParameters = new SubSystemMassParameters(concept);
		subSystemMassParameters.setName(MASS_PARAMETERS);
		subSystem.add(subSystemMassParameters);
		
		SubSystemPowerParameters subSystemPowerParameters = new SubSystemPowerParameters(concept);
		subSystemPowerParameters.setName(POWER_PARAMETERS);
		subSystem.add(subSystemPowerParameters);
	}

	public static void addEquipmentParameters(Concept concept, Equipment equipment) {
		EquipmentParameters equipmentParams = new EquipmentParameters(concept);
		equipmentParams.setUnitQuantity(1);
		equipmentParams.setMarginMaturity(20);
		equipment.add(equipmentParams);
		
		EquipmentMassParameters equipmentMassParameters = new EquipmentMassParameters(concept);
		equipmentMassParameters.setName(EQUIPMENT_MASS_PARAMETERS);
		equipmentMassParameters.getMassPerUnit().setDefaultValue(10);
		equipment.add(equipmentMassParameters);
		
		EquipmentPowerParameters powerParameters = new EquipmentPowerParameters(concept);
		powerParameters.setName(POWER_PARAMETERS);
		equipment.add(powerParameters);
		
		EquipmentTemperatureParameters temperatureParameters = new EquipmentTemperatureParameters(concept);
		temperatureParameters.setName(TEMPERATURE_PARAMETERS);
		temperatureParameters.getTemperatureNoOpsMax().setDefaultValue(30);
		temperatureParameters.getTemperatureNoOpsMin().setDefaultValue(5);
		temperatureParameters.getTemperatureOpsMax().setDefaultValue(60);
		temperatureParameters.getTemperatureOpsMin().setDefaultValue(15);
		equipment.add(temperatureParameters);
	}
	//CHECKSTYLE:ON

	/**
	 * Set child discipline to parent (if any) and create a command to add child
	 * @param parent 
	 * @param child 
	 * @param domain 
	 * @return command that will create file structure for a child and add it to the parent
	 */
	public static CompoundCommand createAddChildSEICommand(EObject parent, StructuralElementInstance child, VirSatTransactionalEditingDomain domain) {
		Discipline discipline = null;
		if (parent instanceof IAssignedDiscipline) {
			discipline = ((IAssignedDiscipline) parent).getAssignedDiscipline();
		}
		child.setAssignedDiscipline(discipline);
		
		CompoundCommand cmd = new CompoundCommand();
		cmd.append(new CreateSeiResourceAndFileCommand(domain.getResourceSet(), child));
		cmd.append(CreateAddStructuralElementInstanceCommand.create(domain, parent, child));
		return cmd;
	}
}