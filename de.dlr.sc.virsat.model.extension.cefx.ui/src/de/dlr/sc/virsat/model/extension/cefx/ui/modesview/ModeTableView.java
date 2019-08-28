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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cefx.model.Parameter;
import de.dlr.sc.virsat.model.extension.cefx.model.SystemMode;
import de.dlr.sc.virsat.model.ui.editor.input.VirSatUriEditorInput;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain.IResourceEventListener;
import de.dlr.sc.virsat.project.ui.comparer.VirSatComparer;

/**
 * UI giving the user a view on parameter values during different modes.
 * @author muel_s8
 *
 */

public class ModeTableView extends ViewPart {

	public static final String VIEW_ID = "de.dlr.sc.virsat.extension.cefuture.ui.modesview.modeTableView";
	
	private static final String TITLE_TEXT_START_TOKEN =  "Mode Overview Table: ";
	
	protected static final int TABLE_COLUMN_SEI_INDX = 0;
	protected static final int TABLE_COLUMN_PARAM_INDX = 1;
	protected static final int TABLE_COLUMN_UNIT_INDX = 2;
	protected static final int TABLE_COLUMN_DEF_INDX = 3;
	protected static final int TABLE_COLUMN_AMOUNT = 4;
	
	private static final String TABLE_COLUMN_SC_NAME = "Component";
	private static final String TABLE_COLUMN_PARAM_NAME = "Parameter";
	private static final String TABLE_COLUMN_UNIT_NAME = "Unit";
	private static final String TABLE_COLUMN_DEF_NAME = "default";
	
	private static final int TABLE_COLUMN_SC_SIZE = 200;
	private static final int TABLE_COLUMN_PARAM_SIZE = 150;
	private static final int TABLE_COLUMN_UNIT_SIZE = 50;
	private static final int TABLE_COLUMN_DEF_SIZE = 100;
	
	private TreeViewer treeViewer;
	private FormToolkit toolkit;
	private ScrolledForm form;
	private StructuralElementInstance sei;
	private boolean isDisposed;
	
	ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection sel) {
			if (!(sel instanceof IStructuredSelection)) {
				return;
			}

			IStructuredSelection ss = (IStructuredSelection) sel;
			Object o = ss.getFirstElement();

			if (o instanceof StructuralElementInstance) {
				updateTable((StructuralElementInstance) o);
			}
		}
	};
	
	private IResourceEventListener eventListener = (Set<Resource> newResources, int event) -> {
		Display.getDefault().asyncExec(() -> {
			if (sei != null) {
				if (event == VirSatTransactionalEditingDomain.EVENT_RELOAD) {
					reloadModel();
				}
				updateTable(sei);
			}
		});
	};
	
	@Override
	public void createPartControl(Composite parent) {
		Display display = parent.getDisplay();
		toolkit = new FormToolkit(display);
		form = toolkit.createScrolledForm(parent);
	
		// Add some heading style to the thing
		toolkit.decorateFormHeading(form.getForm());
		form.setImage(getTitleImage());     
		form.setText(TITLE_TEXT_START_TOKEN);
		
		// Set the Layout for the ScrolledForm
		GridLayout layout = new GridLayout(1, true);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		form.getBody().setLayout(layout);
		
	    Tree tree = toolkit.createTree(form.getBody(), SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	    tree.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	    treeViewer = new TreeViewer(tree);
	    
	    // Add the first two columns
	    addColumnToTree(TABLE_COLUMN_SC_NAME, TABLE_COLUMN_SC_SIZE, SWT.NONE);
	    addColumnToTree(TABLE_COLUMN_PARAM_NAME, TABLE_COLUMN_PARAM_SIZE, SWT.NONE);
	    addColumnToTree(TABLE_COLUMN_UNIT_NAME, TABLE_COLUMN_UNIT_SIZE, SWT.NONE);
	    addColumnToTree(TABLE_COLUMN_DEF_NAME, TABLE_COLUMN_DEF_SIZE, SWT.RIGHT);
		  
	    treeViewer.setContentProvider(new ModeTableContentProvider());
	    
	    tree.setHeaderVisible(true);
	    tree.setLinesVisible(true);

	    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateGraph();
			}
		});
	    
	    treeViewer.setComparer(new VirSatComparer() {
	    	@Override
	    	public boolean equals(Object elementA, Object elementB) {
	    		if (elementA instanceof Parameter) {
	    			elementA = ((Parameter) elementA).getATypeInstance();
	    		}
	    		
	    		if (elementB instanceof Parameter) {
	    			elementB = ((Parameter) elementB).getATypeInstance();
	    		}

	    		return super.equals(elementA, elementB);
	    	}

	    	@Override
	    	public int hashCode(Object element) {
	    		if (element instanceof Parameter) {
	    			element = ((Parameter) element).getATypeInstance();
	    		}
	    		
	    		return super.hashCode(element);
	    	}
	    });
	    
	    treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClick(event);
			}
	    });
	
		// Very important to get the scrollBars right.... another story of Eclipse WTF....
		form.reflow(true);
		
		isDisposed = false;
		getSite().getPage().addSelectionListener(listener);
		VirSatTransactionalEditingDomain.addResourceEventListener(eventListener);
	}
	
	/**
	 * Handles double click events by opening the editor page for the selected
	 * element.
	 * @param anEvent double click event
	 */
	protected void handleDoubleClick(DoubleClickEvent anEvent) {
		IStructuredSelection selection = (IStructuredSelection) anEvent.getSelection();
		Object element = selection.getFirstElement();
		
		if (element instanceof Parameter) {
			EObject eObject = ((Parameter) element).getATypeInstance();
			VirSatUriEditorInput.openDrillDownEditor(eObject); 
		}
	}
	
	@Override
	public void dispose() {
		isDisposed = true;
		getSite().getPage().removeSelectionListener(listener);
		VirSatTransactionalEditingDomain.removeResourceEventListener(eventListener);
		super.dispose();
	}

	// We use this variable to check if the list of modes has changed with the previous
	// Call. If it has changed we will have to update it
	private List<SystemMode> previousModes =  new LinkedList<SystemMode>();
	private SystemMode selectedColumnMode;

	private VirSatTransactionalEditingDomain domain;
	private URI resourceURI; 
	
	/**
	 * Update the mode graph view according to the selection
	 */
	private void updateGraph() {
		// So first we ask the Platform to get the view for the graphs
		IViewReference [] viewReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
		ModeGraphsView modeGraphsView = null;
		
		for (IViewReference viewRef : viewReferences) {
			if (viewRef.getId().equals(ModeGraphsView.VIEW_ID)) {
				modeGraphsView = (ModeGraphsView) viewRef.getView(false);
			}
		}
		
		if (modeGraphsView != null) {
			// Now where we found it, we will tell it which Parameter has to be displayed
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Parameter param = (Parameter) selection.getFirstElement();
			if (param != null) {
				modeGraphsView.setParameter(param);
			}
			
			if (selectedColumnMode != null && selectedColumnMode.getATypeInstance().eResource() != null) {
				modeGraphsView.setSystemMode(selectedColumnMode);
			}
		}
	}
	
	/**
	 * A structural element instance has been selected. Update the table accordingly.
	 * @param sei the selected structural element instance
	 */
	private void updateTable(StructuralElementInstance sei) {
		domain = (VirSatTransactionalEditingDomain) VirSatTransactionalEditingDomain.getEditingDomainFor(sei);
        if (domain == null || isDisposed) {
        	sei = null;
            treeViewer.setInput(null);
            treeViewer.refresh();
            return;
        }
		
		this.sei = sei;
		resourceURI = sei.eResource().getURI();
		
		form.setText(TITLE_TEXT_START_TOKEN + sei.getName());

		// Now get all Modes
		List<SystemMode> currentModes = ModeHelper.getSystemModes(sei);
		
		// Compare if the modes have changed in case they have
		// remember the new set of modes for the future
		// and trigger the update for the table columns
		if (!previousModes.equals(currentModes)) {
			previousModes = new LinkedList<SystemMode>(currentModes);
			
		}
		
		// and now refresh the table itself
		treeViewer.getControl().setRedraw(false);
		Object[] expanded = treeViewer.getExpandedElements();
		updateTableColumns();
		
		treeViewer.setInput(sei);
		treeViewer.refresh();
		treeViewer.setExpandedElements(expanded);
		treeViewer.getControl().setRedraw(true);
	}

	/**
	 * This method takes care of applying the currently available modes
	 * of the currently studied satellite system to the table.
	 * it also takes care of removing redundant modes...
	 */
	private void updateTableColumns() {
		
		// First we need to close the current Columns by disposing them
		// dirty hack but seems to work but we need to be careful because we
		// want to keep the first three standard columns
		for (int i = treeViewer.getTree().getColumnCount() - 1; i >= TABLE_COLUMN_AMOUNT; i--) {
			treeViewer.getTree().getColumn(i).dispose();
		}

		
		// Now where they have been removed we will update the modes
		// according to the currently active modes for the selected system
		// remember modes are stored in the root component but the helper will
		// take care of that. previous modes carries the current modes since the lists
		// have been swapped
		for (SystemMode mode : previousModes) {
			addColumnToTree(mode.getName(), TABLE_COLUMN_DEF_SIZE, SWT.RIGHT);
		}
	
		// The LabelProivder needs to be set here again so that all columns are aware of it
		// if it is not set here, the newly added columns will not have a LabelProvider
		// and refreshing the columns will result in a crash
		treeViewer.setLabelProvider(new ModeTableLabelProvider());
	}
	
	/**
	 * Attaches a new column to the current tree table
	 * @param name the name of the new column
	 * @param width the width of the new column
	 * @param style style of the column
	 */
	private void addColumnToTree(String name, int width, int style) {
		TreeViewerColumn column = new TreeViewerColumn(treeViewer, SWT.NONE | style);
		column.getColumn().setText(name);
		column.getColumn().setWidth(width);
		column.getColumn().setResizable(true);
		column.getColumn().setMoveable(true);
		column.getColumn().addSelectionListener(new ColumnSelectionListener());
	}
	
	/**
	 * 
	 * This listener is added to each column to detect the mode attached to the column
	 * when a column is selected this information will be forwarded to the thing stuff :-D
	 *
	 */
	class ColumnSelectionListener implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			TreeColumn column = (TreeColumn) e.widget;
			String modeName = column.getText();
			selectedColumnMode = ModeHelper.getModeByName((StructuralElementInstance) treeViewer.getInput(), modeName);
			updateGraph();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);			
		}
	}
	
	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
	
	/**
	 * This is the method called to load a resource into the editing domain's resource set based on the editor's input.
	 */
	public void reloadModel() {
		if (sei != null && domain != null) {
			Resource resource = null;
			try {
				// Load the resource through the editing domain.
				resource = domain.getResourceSet().getResource(resourceURI, true);
			} catch (Exception e) {
				resource = domain.getResourceSet().getResource(resourceURI, false);
			}
	
			sei = (StructuralElementInstance) resource.getContents().get(0);
		}
	}
}
