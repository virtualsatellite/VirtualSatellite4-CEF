/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.templates;


import org.eclipse.emf.common.command.CompoundCommand;



import org.eclipse.emf.ecore.EObject;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.IAssignedDiscipline;
import de.dlr.sc.virsat.model.dvlm.roles.Discipline;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.structural.command.CreateAddStructuralElementInstanceCommand;
import de.dlr.sc.virsat.model.extension.cefx.Activator;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.EquipmentTemperatureParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SubSystemPowerParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMassParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemParameters;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemPowerParameters;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.resources.command.CreateSeiResourceAndFileCommand;
/**
 * Set the parameter for the Concepts (System, SubSystem, Equipment)
 *
 */
public class DLRCEFXStudyCommandHelper {
	
	static final int DEFAULT_SYSTEM_MAGRIN = 20;
	static final int DEFAULT_MASS_ADAPTER = 100;
	static final int DEFAULT_MASS_LAUNCH_MAX = 850;
	static final int DEFAULT_MASS_PROPELLANT = 35;
	static final int DEFAULT_TEMPERATURE_NO_OPS_MAX = 30;
	static final int DEFAULT_TEMPERATURE_NO_OPS_MIN = 5;
	static final int DEFAULT_TEMPERATURE_OPS_MAX = 60;
	static final int DEFAULT_TEMPERATURE_OPS_MIN = 15;
	static final int DEFAULT_MARGIN_MATURITY = 20;
	static final int DEFAULT_EQUIPMENT_MASS_PARAMETERS = 10;

	/**
	 * private Constructor
	 */
	private DLRCEFXStudyCommandHelper() {
	}
	/**
	 * Retrieves the CEFX concept associated with the provided domain.
	 * 
	 * @param domain the VirSatTransactionalEditingDomain instance representing the domain
	 * @return the CEFX concept associated with the domain
	 */

	public static Concept getCefxConcept(VirSatTransactionalEditingDomain domain) {
		Repository currentRepository = domain.getResourceSet().getRepository();
		ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
		Concept activeConcept = acHelper.getConcept(Activator.PLUGIN_ID);
		return activeConcept;
	}

	/**
	 * Retrieves the PS (product structure) concept associated with the provided domain.
	 * 
	 * @param domain the VirSatTransactionalEditingDomain instance representing the domain
	 * @return the PS concept associated with the domain
	 */
	public static Concept getPsConcept(VirSatTransactionalEditingDomain domain) {
		Repository currentRepository = domain.getResourceSet().getRepository();
		ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
		Concept activeConcept = acHelper.getConcept(de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId());
		return activeConcept;
	}
	/**
	 * 
	 * @param conceptPs
	 * @return create the system as the configuration tree.
	 */
	public static ConfigurationTree createSystemAsConfigurationTree(Concept conceptPs) {
		
		ConfigurationTree system = new ConfigurationTree(conceptPs);
		system.setName("System");
		return system;
	}
	
	/**
	 * 
	 * @param conceptPs
	 * @return create Sub-system as element configuration
	 */
	public static ElementConfiguration createSubSystemAsElementConfiguration(Concept conceptPs) {
		ElementConfiguration subSystem = new ElementConfiguration(conceptPs);
		subSystem.setName("subSystem");
		return subSystem;
	}

	/**
	 * @param conceptPs
	 * @return create an equipment as element configuration.
	 */
	public static ElementConfiguration createEquipmentAsElementConfiguration(Concept conceptPs) {
		ElementConfiguration equipment = new ElementConfiguration(conceptPs);
		equipment.setName("equipment");
		return equipment;
	}

	/**
	 * @param conceptCEFX, system
	 */
	public static void addSystemParameters(Concept conceptCEFX, ConfigurationTree system) {
		//default unit in concept is percent 
		SystemParameters systemParameters = new SystemParameters(conceptCEFX);
		systemParameters.setName("systemParameters");
		systemParameters.setSystemMargin(DEFAULT_SYSTEM_MAGRIN);  
		system.add(systemParameters);
		
		SystemMassParameters systemMassParameters = new SystemMassParameters(conceptCEFX);
		systemMassParameters.setName("massParameters");
		systemMassParameters.getMassAdapter().setDefaultValue(DEFAULT_MASS_ADAPTER);
		systemMassParameters.getMassLaunchMax().setDefaultValue(DEFAULT_MASS_LAUNCH_MAX);
		systemMassParameters.getMassPropellant().setDefaultValue(DEFAULT_MASS_PROPELLANT);
		system.add(systemMassParameters);
		
		SystemPowerParameters systemPowerParameters = new SystemPowerParameters(conceptCEFX);
		systemPowerParameters.setName("powerParameters");
		system.add(systemPowerParameters);
	}

	/**
	 * @param conceptCEFX, subSystem 
	 */
	public static void addSubSystemParameters(Concept conceptCEFX, ElementConfiguration subSystem) {
		SubSystemMassParameters subSystemMassParameters = new SubSystemMassParameters(conceptCEFX);
		subSystemMassParameters.setName("massParameters");
		subSystem.add(subSystemMassParameters);
		
		SubSystemPowerParameters subSystemPowerParameters = new SubSystemPowerParameters(conceptCEFX);
		subSystemPowerParameters.setName("powerParameters");
		subSystem.add(subSystemPowerParameters);
	}

	/**
	 * @param conceptCEFX, equipment 
	 */
	public static void addEquipmentParameters(Concept conceptCEFX, ElementConfiguration equipment) {
		EquipmentParameters equipmentParams = new EquipmentParameters(conceptCEFX);
		equipmentParams.setMarginMaturity(DEFAULT_MARGIN_MATURITY);
		equipment.add(equipmentParams);
		
		EquipmentMassParameters equipmentMassParameters = new EquipmentMassParameters(conceptCEFX);
		equipmentMassParameters.setName("EquipmentMassParameters");
		equipmentMassParameters.getMass().setDefaultValue(DEFAULT_EQUIPMENT_MASS_PARAMETERS);
		equipment.add(equipmentMassParameters);
		
		EquipmentPowerParameters powerParameters = new EquipmentPowerParameters(conceptCEFX);
		powerParameters.setName("powerParameters");
		equipment.add(powerParameters);
		
		EquipmentTemperatureParameters temperatureParameters = new EquipmentTemperatureParameters(conceptCEFX);
		temperatureParameters.setName("temperatureParameters");
		temperatureParameters.getTemperatureNoOpsMax().setDefaultValue(DEFAULT_TEMPERATURE_NO_OPS_MAX);
		temperatureParameters.getTemperatureNoOpsMin().setDefaultValue(DEFAULT_TEMPERATURE_NO_OPS_MIN);
		temperatureParameters.getTemperatureOpsMax().setDefaultValue(DEFAULT_TEMPERATURE_OPS_MAX);
		temperatureParameters.getTemperatureOpsMin().setDefaultValue(DEFAULT_TEMPERATURE_OPS_MIN);
		equipment.add(temperatureParameters);
	}

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