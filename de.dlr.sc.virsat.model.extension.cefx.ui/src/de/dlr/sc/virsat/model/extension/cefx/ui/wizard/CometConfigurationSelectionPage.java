/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.ui.wizard;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cdp4common.dto.Thing;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4servicesdal.CdpServicesDal;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ArrayInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.project.ui.contentProvider.VirSatFilteredWrappedTreeContentProvider;
import de.dlr.sc.virsat.uiengine.ui.wizard.AImportExportPage;

/**
 * A page to select a CSV file to import and the target model element
 *
 */
public class CometConfigurationSelectionPage extends AImportExportPage {
	private static final String PAGE_TITEL = "Comet Login";
	
	private static final String SERVER_URL_LABEL = "Doors Server Url:";
	private static final String PROJECT_NAME_LABEL = "Doors Project Name:";
	private static final String USER_ID_LABEL = "Doors User ID:";
	private static final String PASSWORD_LABEL = "Doors Password:";
	
	protected static final int COLUMNS = 3;
	protected static final int WITH_TEXT = 200;
	
	private Text serverUrlField;
	private Text projectNameField;
	private Text userIdField;
	private Text passwordField;
	
	private String server;
	private static String project;
	private String user;
	private String password;
	private static final String SECURE_STORAGE_COMET = "Comet user credentials (" + project + ")";

	private Credentials credentials;
	private Session session;
	private CdpServicesDal dal;

	private URI uri = URI.create("https://cdp4services-test.cdp4.org");
	
	/**
	 * Standard constructor
	 * 
	 * @param model            the root model
	 */
	protected CometConfigurationSelectionPage(IContainer model) {
		super(PAGE_TITEL);
		setTitle(PAGE_TITEL);
		setModel(model);
		setDescription("Please choose an existing Doors synchronization configuration.");
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		createPropertiesUI(parent);
		createTreeViewer();
	}

	/**
	 * Create a tree viewer with filters to show only relevant tree elements for
	 * Doors import /export
	 */
	protected TreeViewer createTreeViewer() {
		TreeViewer treeViewer = createTreeUI();
		VirSatFilteredWrappedTreeContentProvider filteredCp = (VirSatFilteredWrappedTreeContentProvider) treeViewer
				.getContentProvider();
		filteredCp.addClassFilter(CategoryAssignment.class);
		filteredCp.addClassFilter(ArrayInstance.class);
		filteredCp.addClassFilter(ComposedPropertyInstance.class);
		return treeViewer;
	}
	
	/**
	 * Create the UI for Doors properties
	 * 
	 * @param parent the parent composite
	 */
	private void createPropertiesUI(Composite parent) {
		Composite doorsComposite = new Composite((Composite) getControl(), SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		doorsComposite.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		Label serverUrlLabel = new Label(doorsComposite, SWT.NONE);
		serverUrlLabel.setText(SERVER_URL_LABEL);

		serverUrlField = new Text(doorsComposite, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.widthHint = WITH_TEXT;
		serverUrlField.setLayoutData(gridData);

		Label projectNameLabel = new Label(doorsComposite, SWT.NONE);
		projectNameLabel.setText(PROJECT_NAME_LABEL);

		projectNameField = new Text(doorsComposite, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.widthHint = WITH_TEXT;
		projectNameField.setLayoutData(gridData);

		Label userIdLabel = new Label(doorsComposite, SWT.NONE);
		userIdLabel.setText(USER_ID_LABEL);

		userIdField = new Text(doorsComposite, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 1;
		gridData.widthHint = WITH_TEXT;
		userIdField.setLayoutData(gridData);

		Label passwordLabel = new Label(doorsComposite, SWT.NONE);
		passwordLabel.setText(PASSWORD_LABEL);

		passwordField = new Text(doorsComposite, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 1;
		gridData.widthHint = WITH_TEXT;
		passwordField.setLayoutData(gridData);

		Button button = new Button(doorsComposite, SWT.BUTTON1);
		button.setText("Add Synchronization Configuration");

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		button.setLayoutData(gridData);
	}

	/**
	 * Check if client can build a connection to the selected Comet server
	 * @return true if connection valid or false
	 */
	public boolean isCometConnectionValid() {
		server = serverUrlField.getText();
		project = projectNameField.getText();
		user = userIdField.getText();
		password = passwordField.getText();
		this.uri = URI.create("https://cdp4services-test.cdp4.org");
		
		this.credentials = new Credentials("admin", "pass", this.uri, null);
		this.dal = new CdpServicesDal();
		this.session = new SessionImpl(this.dal, this.credentials);
		

	    try {
			List<Thing> result = this.dal.open(this.credentials, new AtomicBoolean()).get();
			System.out.println(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}


		return false;
	}

	/**
	 * If an existing configuration is selected the wizard can be finished otherwise
	 * we need the next side
	 * 
	 * @return if wizard can be finished
	 */
	public boolean canFinish() {
		return isCometConnectionValid();
	}

	@Override
	public boolean canFlipToNextPage() {
		return super.canFlipToNextPage() && !canFinish();
	}

	@Override
	public boolean isComplete() {
		return isCurrentPage();
	}

	@Override
	public Object getSelection() {
		Object selected = super.getSelection();
		if (selected instanceof ComposedPropertyInstance) {
			selected = ((ComposedPropertyInstance) selected).getTypeInstance();
		}
		return selected;
	}


	/**
	 * 
	 * Method to check if connection to the doors server is possible
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private boolean isDoorsConnectionValid() {
		
		
		return true;
	}


	@Override
	public CometImportWizard getWizard() {
		return (CometImportWizard) super.getWizard();
	}
}