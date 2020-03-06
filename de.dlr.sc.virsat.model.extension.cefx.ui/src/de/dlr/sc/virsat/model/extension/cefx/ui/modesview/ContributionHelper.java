/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.modesview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.dlr.sc.virsat.commons.datastructures.DependencyTree;
import de.dlr.sc.virsat.model.calculation.compute.EquationHelper;
import de.dlr.sc.virsat.model.dvlm.calculation.Equation;
import de.dlr.sc.virsat.model.dvlm.calculation.IEquationResult;
import de.dlr.sc.virsat.model.dvlm.calculation.TypeInstanceResult;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.ui.Activator;
import de.dlr.sc.virsat.project.editingDomain.VirSatEditingDomainRegistry;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;

/**
 * Helper class to get parameters contributing to make up the value of a calculated parameter.
 * Also provides help in actually computing the contribution.
 * @author muel_s8
 *
 */

public class ContributionHelper {
	
	private Parameter parameter;
	
	/**
	 * Public constructor
	 * @param parameter the wrapper parameter
	 */
	
	public ContributionHelper(Parameter parameter) {
		this.parameter = parameter;
	}
	
	/**
	 * Get all parameters that have some kind of influence on this parameter
	 * @return list of parameters influencing this parameter
	 */
	
	public List<Parameter> getSourceParameters() {
		Equation equation = getEquation();
		
		List<Parameter> sourceParameters = new ArrayList<>();
		
		if (equation != null) {
			EquationHelper dtHelper = new EquationHelper();
			DependencyTree<EObject> dt = dtHelper.createDependencyTree(equation);
			List<EObject> dependencies = dt.getLinearOrder();
			
			for (EObject object : dependencies) {
				if (object != parameter.getTypeInstance() && object instanceof CategoryAssignment) {
					CategoryAssignment ca = (CategoryAssignment) object;
					if (ca.getType().equals(parameter.getTypeInstance().getType())) {
						sourceParameters.add(new Parameter(ca));
					}
				}
			}
		}
		
		return sourceParameters;
	}

	/**
	 * the method getAllEquationsInProject crawls through the whole project and gathers all Equations.
	 * It also resolves all proxied elements in our project
	 * @return equation responsible for setting the value the value of the parameter, null if
	 * no such equation exists
	 */
	public Equation getEquation() {
		try {
			EObject instance = parameter.getATypeInstance();
			
			// First detect the editing domain
			VirSatTransactionalEditingDomain ed = VirSatEditingDomainRegistry.INSTANCE.getEd(instance);
			if (ed != null) {
				ResourceSet resourceSet = ed.getResourceSet();
				
				for (TreeIterator<Object> iter = EcoreUtil.getAllContents(resourceSet, true); iter.hasNext();) {
					Object object = iter.next();
					if (object instanceof Equation) {
						Equation equation = (Equation) object;
						IEquationResult result = equation.getResult();
						if (result instanceof TypeInstanceResult &&	((TypeInstanceResult) result).getReference() == instance) {
							return equation;
						}
					}
				}
			}
		} catch (Exception e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Could not read active Equation from Repository", e));
		}
		
		return null;
	};
}
