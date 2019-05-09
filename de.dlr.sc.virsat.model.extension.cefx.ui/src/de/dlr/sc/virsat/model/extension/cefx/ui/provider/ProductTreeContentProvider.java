package de.dlr.sc.virsat.model.extension.cefx.ui.provider;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.model.extension.ps.model.ElementDefinition;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTree;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTreeDomain;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatFilteredWrappedTreeContentProvider;
import de.dlr.sc.virsat.project.ui.navigator.contentProvider.VirSatProjectContentProvider;

public class ProductTreeContentProvider extends VirSatFilteredWrappedTreeContentProvider {

	public ProductTreeContentProvider() {
		super(new VirSatProjectContentProvider());
		addStructuralElementIdFilter(ProductTree.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		addStructuralElementIdFilter(ProductTreeDomain.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		addStructuralElementIdFilter(ElementDefinition.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
	}

}
