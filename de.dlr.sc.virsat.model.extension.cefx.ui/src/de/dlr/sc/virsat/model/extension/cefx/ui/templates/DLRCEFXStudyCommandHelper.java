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


import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;



import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;

import de.dlr.sc.virsat.model.dvlm.Repository;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.IAssignedDiscipline;
import de.dlr.sc.virsat.model.dvlm.roles.Discipline;
import de.dlr.sc.virsat.model.dvlm.roles.RoleManagement;
import de.dlr.sc.virsat.model.dvlm.roles.RolesFactory;
import de.dlr.sc.virsat.model.dvlm.roles.RolesPackage;
import de.dlr.sc.virsat.model.dvlm.roles.UserRegistry;
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
import de.dlr.sc.virsat.model.extension.ps.model.ElementDefinition;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTree;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTreeDomain;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.resources.command.CreateSeiResourceAndFileCommand;
/**
 * Set the parameter for the Concepts (System, SubSystem, Equipment)
 *
 */
public class DLRCEFXStudyCommandHelper {
	
	/**
	 * private Constructor
	 */
	private DLRCEFXStudyCommandHelper() {
	}
	/**
	 * Get the cefx concept from the domain
	 * @param domain the domain
	 * @return the cefx concept
	 */
	public static Concept getCefxConcept(VirSatTransactionalEditingDomain domain) {
		Repository currentRepository = domain.getResourceSet().getRepository();
		ActiveConceptHelper acHelper = new ActiveConceptHelper(currentRepository);
		Concept activeConcept = acHelper.getConcept(Activator.PLUGIN_ID);
		return activeConcept;
	}

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
	 * @return create the system as the product tree.
	 */
	public static ProductTree createSystemAsProductTree(Concept conceptPs) {
		
		ProductTree system = new ProductTree(conceptPs);
		system.setName("ProductTree");
		return system;
	}
	
	/**
	 * 
	 * @param conceptPs
	 * @return create Sub-system as product tree domain
	 */
	public static ProductTreeDomain createSubSystemAsProductTreeDomain(Concept conceptPs) {
		ProductTreeDomain productTreeDomain = new ProductTreeDomain(conceptPs);
		productTreeDomain.setName("PTD");
		return productTreeDomain;
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
	 * @return create an equipment as element definition.
	 */
	public static ElementDefinition createEquipmentAsElementDefinition(Concept conceptPs) {
		ElementDefinition equipment = new ElementDefinition(conceptPs);
		equipment.setName("ReactionWheel");
		return equipment;
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

	//CHECKSTYLE:OFF
	public static void addSystemParameters(Concept conceptCEFX, ConfigurationTree system) {
		//default unit in concept is percent 
		SystemParameters systemParameters = new SystemParameters(conceptCEFX);
		systemParameters.setName("systemParameters");
		systemParameters.setSystemMargin(20);  
		system.add(systemParameters);
		
		
		
		SystemMassParameters systemMassParameters = new SystemMassParameters(conceptCEFX);
		systemMassParameters.setName("massParameters");
		systemMassParameters.getMassAdapter().setDefaultValue(100);
		systemMassParameters.getMassLaunchMax().setDefaultValue(850);
		systemMassParameters.getMassPropellant().setDefaultValue(35);
		system.add(systemMassParameters);
		
		SystemPowerParameters systemPowerParameters = new SystemPowerParameters(conceptCEFX);
		systemPowerParameters.setName("powerParameters");
		system.add(systemPowerParameters);
	}

	public static void addProductTreeParameters(Concept conceptCEFX, ProductTree productTree) {

	}
	
	public static void addProductTreeDomainParameters(Concept conceptCEFX, ProductTreeDomain productTreeDomain) {

	}
	public static void addSubSystemParameters(Concept conceptCEFX, ElementConfiguration subSystem) {
		SubSystemMassParameters subSystemMassParameters = new SubSystemMassParameters(conceptCEFX);
		subSystemMassParameters.setName("massParameters");
		subSystem.add(subSystemMassParameters);
		
		SubSystemPowerParameters subSystemPowerParameters = new SubSystemPowerParameters(conceptCEFX);
		subSystemPowerParameters.setName("powerParameters");
		subSystem.add(subSystemPowerParameters);
	}

	public static void addEquipmentParameters(Concept conceptCEFX, ElementConfiguration equipment) {
		EquipmentParameters equipmentParams = new EquipmentParameters(conceptCEFX);
		equipmentParams.setMarginMaturity(20);
		equipment.add(equipmentParams);
		
		EquipmentMassParameters equipmentMassParameters = new EquipmentMassParameters(conceptCEFX);
		equipmentMassParameters.setName("EquipmentMassParameters");
		equipmentMassParameters.getMass().setDefaultValue(10);
		equipment.add(equipmentMassParameters);
		
		EquipmentPowerParameters powerParameters = new EquipmentPowerParameters(conceptCEFX);
		powerParameters.setName("powerParameters");
		equipment.add(powerParameters);
		
		EquipmentTemperatureParameters temperatureParameters = new EquipmentTemperatureParameters(conceptCEFX);
		temperatureParameters.setName("temperatureParameters");
		temperatureParameters.getTemperatureNoOpsMax().setDefaultValue(30);
		temperatureParameters.getTemperatureNoOpsMin().setDefaultValue(5);
		temperatureParameters.getTemperatureOpsMax().setDefaultValue(60);
		temperatureParameters.getTemperatureOpsMin().setDefaultValue(15);
		equipment.add(temperatureParameters);
	}
	public static void addElementDefinitionParameters(Concept conceptCEFX, ElementDefinition elementDefinition) {
		EquipmentParameters equipmentParams = new EquipmentParameters(conceptCEFX);
		equipmentParams.setMarginMaturity(20);
		elementDefinition.add(equipmentParams);
		
		EquipmentMassParameters equipmentMassParameters = new EquipmentMassParameters(conceptCEFX);
		equipmentMassParameters.setName("EquipmentMassParameters");
		equipmentMassParameters.getMass().setDefaultValue(10);
		elementDefinition.add(equipmentMassParameters);
		

	}
	
	// Keep track of created disciplines
	private static Set<String> createdDisciplines = new HashSet<>();

	public static void createDiscipline(VirSatTransactionalEditingDomain domain, String disciplineName) {
	    if (!createdDisciplines.contains(disciplineName)) {
	        RoleManagement roleManagement = domain.getResourceSet().getRoleManagement();
	        Discipline newDiscipline = RolesFactory.eINSTANCE.createDiscipline();
	        newDiscipline.setName(disciplineName);
	        newDiscipline.getUsers().add(UserRegistry.getInstance().getUserName());
	        Command addCommand = AddCommand.create(domain, roleManagement, RolesPackage.eINSTANCE.getRoleManagement_Disciplines(), newDiscipline);
	        domain.getCommandStack().execute(addCommand);

	        // Add discipline name to the set to avoid creating it again
	        createdDisciplines.add(disciplineName);
	    }
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

		//AddCommand.create(domain, roleManagement, RolesPackage.ROLE_MANAGEMENT__DISCIPLINES, newDiscipline);
		
		//roleManagement.getDisciplines().add(newDiscipline);
					
		CompoundCommand cmd = new CompoundCommand();
		cmd.append(new CreateSeiResourceAndFileCommand(domain.getResourceSet(), child));
		cmd.append(CreateAddStructuralElementInstanceCommand.create(domain, parent, child));
		return cmd;
	}
}