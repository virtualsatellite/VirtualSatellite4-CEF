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

import java.util.stream.IntStream;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;

import de.dlr.sc.virsat.model.concept.types.roles.BeanDiscipline;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.roles.Discipline;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.model.extension.ps.model.ElementDefinition;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTree;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTreeDomain;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

/**
 * Creates System -> SubSystem -> Equipment data to enable a quick study.
 *
 */
public class CreateDLRCEFXStudyCommand {
	
	static final String POWER_NAME = "Power";
	static final String STRUCTURE_NAME = "Structure";
	static final String AOCS_NAME = "AOCS";
	static final String PAYLOAD_NAME = "Payload";
	static final String DATAHANDLING_NAME = "DataHandling";

	/**
	 * Private constructor.
	 */
	private CreateDLRCEFXStudyCommand() {
	}

	/**
	 * Creates a CompoundCommand for adding a new system structure based on the given Concept.
	 *
	 * <p>This method generates a system structure consisting of a ConfigurationTree, a SubSystem,
	 * and an Equipment. It then creates commands to add these structural elements as child elements
	 * in a hierarchical manner. Finally, it records commands to add system, sub-system, and equipment
	 * parameters using the DLRCEFXStudyCommandHelper.</p>
	 *
	 * @param parent The parent EObject to which the system structure will be added.
	 * @param concept The Concept used to create the system structure.
	 * @param domain The VirSatTransactionalEditingDomain in which the command will be executed.
	 * @return A CompoundCommand representing the operation to create the system structure.
	 */
	public static CompoundCommand create(EObject parent, Concept conceptPs, Concept conceptCefx, VirSatTransactionalEditingDomain domain) {
	    // Create system structure elements
	    ConfigurationTree system = DLRCEFXStudyCommandHelper.createSystemAsConfigurationTree(conceptPs);
	    
	    ProductTree productTree = DLRCEFXStudyCommandHelper.createSystemAsProductTree(conceptPs);
	    
	    ElementConfiguration powerEquipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(conceptPs);
	    ElementConfiguration structureEquipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(conceptPs);
	    ElementConfiguration aocsEquipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(conceptPs);
	    ElementConfiguration payloadEquipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(conceptPs);
	    ElementConfiguration dataHandlingEquipment = DLRCEFXStudyCommandHelper.createEquipmentAsElementConfiguration(conceptPs);
	    
	    ElementDefinition powerElementDefinition = DLRCEFXStudyCommandHelper.createEquipmentAsElementDefinition(conceptPs);
	    ElementDefinition structureElementDefinition = DLRCEFXStudyCommandHelper.createEquipmentAsElementDefinition(conceptPs);
	    ElementDefinition aocsElementDefinition = DLRCEFXStudyCommandHelper.createEquipmentAsElementDefinition(conceptPs);
	    ElementDefinition payloadElementDefinition = DLRCEFXStudyCommandHelper.createEquipmentAsElementDefinition(conceptPs);
	    ElementDefinition dataHandlingElementDefinition = DLRCEFXStudyCommandHelper.createEquipmentAsElementDefinition(conceptPs);

	 // Create default subsystems and assign disciplines    
	    ProductTreeDomain powerProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    powerProductTreeDomain.setName(POWER_NAME);
	    ProductTreeDomain structureProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    structureProductTreeDomain.setName(STRUCTURE_NAME);
	    ProductTreeDomain aocsProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    aocsProductTreeDomain.setName(AOCS_NAME);
	    ProductTreeDomain payloadProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    payloadProductTreeDomain.setName(PAYLOAD_NAME);
	    ProductTreeDomain dataHandlingProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    dataHandlingProductTreeDomain.setName(DATAHANDLING_NAME);
	    
	    ElementConfiguration powerSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    powerSubsystem.setName(POWER_NAME);    
	    ElementConfiguration structureSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    structureSubsystem.setName(STRUCTURE_NAME);
	    ElementConfiguration aocsSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    aocsSubsystem.setName(AOCS_NAME);
	    ElementConfiguration payloadSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    payloadSubsystem.setName(PAYLOAD_NAME);    
	    ElementConfiguration dataHandlingSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    dataHandlingSubsystem.setName(DATAHANDLING_NAME);
	    

	    
	    // Create a CompoundCommand to store sub-commands
	    CompoundCommand cmd = new CompoundCommand();
	    
	    
	    // Create the disciplines or get references, if they already exist
	    
	
	    
	    var powerBeanDiscipline = handleDisciplineCreation(domain, POWER_NAME, cmd);
		var strucureBeanDiscipline = handleDisciplineCreation(domain, STRUCTURE_NAME, cmd);
	    var aocsBeanDiscipline = handleDisciplineCreation(domain, AOCS_NAME, cmd);
	    var payloadBeanDiscipline = handleDisciplineCreation(domain, PAYLOAD_NAME, cmd);
	    var dataHandlingBeanDiscipline = handleDisciplineCreation(domain, DATAHANDLING_NAME, cmd);

	    // Append commands to add child structural elements
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, productTree.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, system.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), powerProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(powerProductTreeDomain.getStructuralElementInstance(), powerElementDefinition.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), aocsProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(aocsProductTreeDomain.getStructuralElementInstance(), aocsElementDefinition.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), payloadProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(payloadProductTreeDomain.getStructuralElementInstance(), payloadElementDefinition.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), structureProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(structureProductTreeDomain.getStructuralElementInstance(), structureElementDefinition.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), dataHandlingProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(dataHandlingProductTreeDomain.getStructuralElementInstance(), dataHandlingElementDefinition.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), powerSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), aocsSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), payloadSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), structureSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), dataHandlingSubsystem.getStructuralElementInstance(), domain));
	    
	    // Append recording command to add system, sub-system, and equipment parameters
	    cmd.append(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
	            DLRCEFXStudyCommandHelper.addSystemParameters(conceptCefx, system);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, powerSubsystem);	            	   
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, powerElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, aocsSubsystem);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, aocsElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, payloadSubsystem);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, payloadElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, structureSubsystem);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, structureElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, dataHandlingSubsystem);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, dataHandlingElementDefinition);
	            
	            // Assign Disciplines
	            powerProductTreeDomain.setAssignedDiscipline(powerBeanDiscipline);
	            powerSubsystem.setAssignedDiscipline(powerBeanDiscipline);
	            powerEquipment.setAssignedDiscipline(powerBeanDiscipline);
	    		powerElementDefinition.setAssignedDiscipline(powerBeanDiscipline);
	    		
	    		aocsProductTreeDomain.setAssignedDiscipline(aocsBeanDiscipline);
	    		aocsSubsystem.setAssignedDiscipline(aocsBeanDiscipline);
	    	    aocsEquipment.setAssignedDiscipline(aocsBeanDiscipline);
	    	    aocsElementDefinition.setAssignedDiscipline(aocsBeanDiscipline);
	    		
	    	    structureProductTreeDomain.setAssignedDiscipline(strucureBeanDiscipline);
	    	    structureSubsystem.setAssignedDiscipline(strucureBeanDiscipline);
	    		structureElementDefinition.setAssignedDiscipline(strucureBeanDiscipline);
	    	    structureEquipment.setAssignedDiscipline(strucureBeanDiscipline);
	    	    
	    	    payloadProductTreeDomain.setAssignedDiscipline(payloadBeanDiscipline);
	    	    payloadSubsystem.setAssignedDiscipline(payloadBeanDiscipline);
	    	    payloadEquipment.setAssignedDiscipline(payloadBeanDiscipline);
	    	    payloadElementDefinition.setAssignedDiscipline(payloadBeanDiscipline);
	    	    
	    	    dataHandlingProductTreeDomain.setAssignedDiscipline(dataHandlingBeanDiscipline);
	    	    dataHandlingSubsystem.setAssignedDiscipline(dataHandlingBeanDiscipline);
	    	    dataHandlingEquipment.setAssignedDiscipline(dataHandlingBeanDiscipline);
	    	    dataHandlingElementDefinition.setAssignedDiscipline(dataHandlingBeanDiscipline);
	    	    
	        }
	    });

	    // Return the CompoundCommand representing the overall operation
	    return cmd;
	}

	private static int getIndexOfDisciplineAlreadyExists(EList<Discipline> list, String disciplineName) {
		return IntStream.range(0, list.size())
	     .filter(i -> list.get(i).getName().equals(disciplineName))
	     .findFirst()
	     .orElse(-1);
	}
	/**
	 * Handles the aggregation of a discipline
	 * @param domain current virsat doamin
	 * @param disciplineName name of the discipline which should be aggregated
	 * @param cmd compoundcommand to add to if a discipline has to be created
	 * @return a new discipline if the name is not already existing, if not it creates a new one and appends a add command
	 */
	private static BeanDiscipline handleDisciplineCreation(VirSatTransactionalEditingDomain domain, String disciplineName, CompoundCommand cmd) {
	    var roleManagement = domain.getResourceSet().getRoleManagement();
	    var existingDisciplines = roleManagement.getDisciplines();
	    var index = getIndexOfDisciplineAlreadyExists(existingDisciplines, POWER_NAME);
	    
		BeanDiscipline discipline = index == -1
				? DLRCEFXStudyCommandHelper.createDiscipline(domain, disciplineName)
				: new BeanDiscipline(existingDisciplines.get(index));

		if (index == -1) {
			cmd.append(DLRCEFXStudyCommandHelper.createDisciplineCommand(domain, discipline));
		}
		return discipline;
	}
}
