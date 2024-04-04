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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;

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
	 * This method generates a system structure consisting of a ConfigurationTree, a SubSystem,
	 * and an Equipment. It then creates commands to add these structural elements as child elements
	 * in a hierarchical manner. Finally, it records commands to add system, sub-system, and equipment
	 * parameters using the DLRCEFXStudyCommandHelper.
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
	    
	    // Create disciplines
	    Discipline powerDiscipline = DLRCEFXStudyCommandHelper.createDisciplineForSubSystemName(POWER_NAME, domain);
	    Discipline structureDiscipline = DLRCEFXStudyCommandHelper.createDisciplineForSubSystemName(STRUCTURE_NAME, domain);	    
	    Discipline aocsDiscipline = DLRCEFXStudyCommandHelper.createDisciplineForSubSystemName(AOCS_NAME, domain);
	    Discipline payloadDiscipline = DLRCEFXStudyCommandHelper.createDisciplineForSubSystemName(PAYLOAD_NAME, domain);
	    Discipline dataHandlingDiscipline = DLRCEFXStudyCommandHelper.createDisciplineForSubSystemName(DATAHANDLING_NAME, domain);
	    
	    
	    aocsElementDefinition.getStructuralElementInstance().setAssignedDiscipline(aocsDiscipline);
	    aocsSubsystem.getStructuralElementInstance().setAssignedDiscipline(aocsDiscipline);
	    
	    payloadElementDefinition.getStructuralElementInstance().setAssignedDiscipline(payloadDiscipline);
	    payloadSubsystem.getStructuralElementInstance().setAssignedDiscipline(payloadDiscipline);
	    
	    dataHandlingElementDefinition.getStructuralElementInstance().setAssignedDiscipline(dataHandlingDiscipline);
	    dataHandlingSubsystem.getStructuralElementInstance().setAssignedDiscipline(dataHandlingDiscipline);
	    
	    // Create a CompoundCommand to store sub-commands
	    CompoundCommand cmd = new CompoundCommand();

	    // Append commands to add child structural elements
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, productTree.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, system.getStructuralElementInstance(), domain));
	    
	    appendIfNotNull(cmd, DLRCEFXStudyCommandHelper.createAddDisciplineCommand(powerDiscipline, domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), powerProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(powerProductTreeDomain.getStructuralElementInstance(), powerElementDefinition.getStructuralElementInstance(), domain));
	    
	    appendIfNotNull(cmd, DLRCEFXStudyCommandHelper.createAddDisciplineCommand(aocsDiscipline, domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), aocsProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(aocsProductTreeDomain.getStructuralElementInstance(), aocsElementDefinition.getStructuralElementInstance(), domain));

	    appendIfNotNull(cmd, DLRCEFXStudyCommandHelper.createAddDisciplineCommand(payloadDiscipline, domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), payloadProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(payloadProductTreeDomain.getStructuralElementInstance(), payloadElementDefinition.getStructuralElementInstance(), domain));
	    
	    appendIfNotNull(cmd, DLRCEFXStudyCommandHelper.createAddDisciplineCommand(structureDiscipline, domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(productTree.getStructuralElementInstance(), structureProductTreeDomain.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(structureProductTreeDomain.getStructuralElementInstance(), structureElementDefinition.getStructuralElementInstance(), domain));
	    
	    appendIfNotNull(cmd, DLRCEFXStudyCommandHelper.createAddDisciplineCommand(dataHandlingDiscipline, domain));
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
	            
	            // Apply diciplines
	            powerProductTreeDomain.getStructuralElementInstance().setAssignedDiscipline(powerDiscipline);
	            powerElementDefinition.getStructuralElementInstance().setAssignedDiscipline(powerDiscipline);
	    	    powerSubsystem.getStructuralElementInstance().setAssignedDiscipline(powerDiscipline);
	    	    
	    	    structureProductTreeDomain.getStructuralElementInstance().setAssignedDiscipline(structureDiscipline);
	            structureElementDefinition.getStructuralElementInstance().setAssignedDiscipline(structureDiscipline);
	    	    structureSubsystem.getStructuralElementInstance().setAssignedDiscipline(structureDiscipline);
	    	    
	    	    aocsProductTreeDomain.getStructuralElementInstance().setAssignedDiscipline(aocsDiscipline);
	            aocsElementDefinition.getStructuralElementInstance().setAssignedDiscipline(aocsDiscipline);
	    	    aocsSubsystem.getStructuralElementInstance().setAssignedDiscipline(aocsDiscipline);
	    	    
	    	    payloadProductTreeDomain.getStructuralElementInstance().setAssignedDiscipline(payloadDiscipline);
	            payloadElementDefinition.getStructuralElementInstance().setAssignedDiscipline(payloadDiscipline);
	    	    payloadSubsystem.getStructuralElementInstance().setAssignedDiscipline(payloadDiscipline);
	    	    
	    	    dataHandlingProductTreeDomain.getStructuralElementInstance().setAssignedDiscipline(dataHandlingDiscipline);
	            dataHandlingElementDefinition.getStructuralElementInstance().setAssignedDiscipline(dataHandlingDiscipline);
	    	    dataHandlingSubsystem.getStructuralElementInstance().setAssignedDiscipline(dataHandlingDiscipline);
	        }
	    });

	    // Return the CompoundCommand representing the overall operation
	    return cmd;
	}
	
	/**
	 * Check if command to be added is valid - and do so
	 * @param cc the Compound command
	 * @param command the command to be added 
	 */
	protected static void appendIfNotNull(CompoundCommand cc, Command command) {
		if (command != null && command.canExecute()) {
			cc.append(command);
		} 
	}


}
