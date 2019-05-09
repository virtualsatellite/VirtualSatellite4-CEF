package de.dlr.sc.virsat.model.extension.cefx.ui.provider;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatFilteredWrappedTreeContentProvider;
import de.dlr.sc.virsat.project.ui.navigator.contentProvider.VirSatProjectContentProvider;

public class ConfigurationTreeContentProvider extends VirSatFilteredWrappedTreeContentProvider {

	public ConfigurationTreeContentProvider() {
		super(new VirSatProjectContentProvider());
		addStructuralElementIdFilter(ConfigurationTree.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		addStructuralElementIdFilter(ElementConfiguration.FULL_QUALIFIED_STRUCTURAL_ELEMENT_NAME);
		
	}

}
