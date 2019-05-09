package de.dlr.sc.virsat.model.extension.cefx.ui.dropAssist;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.TransferData;

import de.dlr.sc.virsat.project.ui.navigator.dropAssist.DVLMObjectDropAdapterAssistant;

public class CEFutureConfigurationInheritanceDropAdapterAssistant extends DVLMObjectDropAdapterAssistant {

	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		// TODO Auto-generated method stub
		
		return Status.OK_STATUS;
	}
}
