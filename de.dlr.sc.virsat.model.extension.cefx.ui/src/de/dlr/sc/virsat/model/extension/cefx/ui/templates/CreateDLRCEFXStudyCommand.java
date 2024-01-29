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
import org.eclipse.emf.transaction.RecordingCommand;

import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
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

	 // Create default subsystems
	    
	    ProductTreeDomain powerProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    powerProductTreeDomain.setName("Power");
	    ProductTreeDomain structureProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    structureProductTreeDomain.setName("Structure");
	    ProductTreeDomain aocsProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    aocsProductTreeDomain.setName("AOCS");
	    ProductTreeDomain payloadProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    payloadProductTreeDomain.setName("Payload");
	    ProductTreeDomain dataHandlingProductTreeDomain = DLRCEFXStudyCommandHelper.createSubSystemAsProductTreeDomain(conceptPs);
	    dataHandlingProductTreeDomain.setName("DataHandling");
	    
	    ElementConfiguration powerSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    powerSubsystem.setName("Power");    
	    ElementConfiguration structureSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    structureSubsystem.setName("Structure");
	    ElementConfiguration aocsSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    aocsSubsystem.setName("AOCS");
	    ElementConfiguration payloadSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    payloadSubsystem.setName("Payload");    
	    ElementConfiguration dataHandlingSubsystem = DLRCEFXStudyCommandHelper.createSubSystemAsElementConfiguration(conceptPs);
	    dataHandlingSubsystem.setName("DataHandling");
	    
	    DLRCEFXStudyCommandHelper.createDiscipline(domain, "Power");
	    DLRCEFXStudyCommandHelper.createDiscipline(domain, "Structure");
	    DLRCEFXStudyCommandHelper.createDiscipline(domain, "AOCS");
	    DLRCEFXStudyCommandHelper.createDiscipline(domain, "Payload");
	    DLRCEFXStudyCommandHelper.createDiscipline(domain, "DataHandling");
	    
	    // Create a CompoundCommand to store sub-commands
	    CompoundCommand cmd = new CompoundCommand();

	    // Append commands to add child structural elements
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, system.getStructuralElementInstance(), domain));
	    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(parent, productTree.getStructuralElementInstance(), domain));
	    
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
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(powerSubsystem.getStructuralElementInstance(), powerEquipment.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), aocsSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(aocsSubsystem.getStructuralElementInstance(), aocsEquipment.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), payloadSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(payloadSubsystem.getStructuralElementInstance(), payloadEquipment.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), structureSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(structureSubsystem.getStructuralElementInstance(), structureEquipment.getStructuralElementInstance(), domain));    
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(system.getStructuralElementInstance(), dataHandlingSubsystem.getStructuralElementInstance(), domain));
	    cmd.append(DLRCEFXStudyCommandHelper.createAddChildSEICommand(dataHandlingSubsystem.getStructuralElementInstance(), dataHandlingEquipment.getStructuralElementInstance(), domain));
	    
	    
	    // Append recording command to add system, sub-system, and equipment parameters
	    cmd.append(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
	            DLRCEFXStudyCommandHelper.addSystemParameters(conceptCefx, system);
	            //DLRCEFXStudyCommandHelper.addProductTreeParameters(conceptCefx, productTree);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, powerSubsystem);
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(conceptCefx, powerEquipment);	            	   
	            //DLRCEFXStudyCommandHelper.addProductTreeDomainParameters(conceptCefx, powerProductTreeDomain);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, powerElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, aocsSubsystem);
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(conceptCefx, aocsEquipment);
	            //DLRCEFXStudyCommandHelper.addProductTreeDomainParameters(conceptCefx, aocsProductTreeDomain);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, aocsElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, payloadSubsystem);
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(conceptCefx, payloadEquipment);
	            //DLRCEFXStudyCommandHelper.addProductTreeDomainParameters(conceptCefx, payloadProductTreeDomain);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, payloadElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, structureSubsystem);
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(conceptCefx, structureEquipment);
	            //DLRCEFXStudyCommandHelper.addProductTreeDomainParameters(conceptCefx, structureProductTreeDomain);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, structureElementDefinition);
	            
	            DLRCEFXStudyCommandHelper.addSubSystemParameters(conceptCefx, dataHandlingSubsystem);
	            DLRCEFXStudyCommandHelper.addEquipmentParameters(conceptCefx, dataHandlingEquipment);
	            //DLRCEFXStudyCommandHelper.addProductTreeDomainParameters(conceptCefx, dataHandlingProductTreeDomain);
	            DLRCEFXStudyCommandHelper.addElementDefinitionParameters(conceptCefx, dataHandlingElementDefinition);
	        }
	    });

	    // Return the CompoundCommand representing the overall operation
	    return cmd;
	}

}
