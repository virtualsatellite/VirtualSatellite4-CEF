
/*******************************************************************************
 * Copyright (c) 2024 German Aerospace Center (DLR), Institute for Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package de.dlr.sc.virsat.cef.loadtest.startup;

import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPartName;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IStartup;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringStartsWith;

import de.dlr.sc.virsat.cef.loadtest.model.DragAndDroppedItem;
import de.dlr.sc.virsat.concept.unittest.util.ConceptXmiLoader;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.IConceptTypeDefinition;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.dvlm.general.IQualifiedName;
import de.dlr.sc.virsat.model.dvlm.util.DVLMItemNaming;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.ps.model.ConfigurationTree;
import de.dlr.sc.virsat.model.extension.ps.model.ElementConfiguration;
import de.dlr.sc.virsat.model.extension.ps.model.ElementDefinition;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTree;
import de.dlr.sc.virsat.model.extension.ps.model.ProductTreeDomain;
import de.dlr.sc.virsat.project.editingDomain.VirSatEditingDomainRegistry;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.editingDomain.commands.VirSatEditingDomainClipBoard;
import de.dlr.sc.virsat.team.ui.util.AutoCheckout;

public class StartLoadTest implements IStartup {
	
	protected static final int SWTBOT_GENERAL_WAIT_TIME = 50; 
	protected static final String SWTBOT_TEST_PROJECTNAME = "SWTBotTestProject";
	
	protected SWTWorkbenchBot bot = new SWTWorkbenchBot();
	protected WorkspaceBuilderInterlockedExecution buildCounter = new WorkspaceBuilderInterlockedExecution();
	protected String projectName;
	protected Concept conceptPs;
	protected Concept conceptCEFXExtension;
	protected List<DragAndDroppedItem> dragAndDroppedItemList;
	protected int loop;
	
	@Override
	public void earlyStartup() {
		System.out.println("hello world");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		beforeLoadTest();
		AutoCheckout autoCheckout = new AutoCheckout();
		autoCheckout.storeCredentials();
		autoCheckout.performAutocheckout();
		getAutoCheckoutProject();
		
		waitForEditingDomainAndUiThread();
		executeLoadTest();
	}
	
	private void beforeLoadTest() { 
		VirSatEditingDomainClipBoard.INSTANCE.clear();
		VirSatEditingDomainRegistry.INSTANCE.clear();  
		VirSatTransactionalEditingDomain.clearAccumulatedRecourceChangeEvents();

		dragAndDroppedItemList = new ArrayList<>();
		conceptPs = ConceptXmiLoader.loadConceptFromPlugin(de.dlr.sc.virsat.model.extension.ps.Activator.getPluginId() + "/concept/concept.xmi");
		conceptCEFXExtension = ConceptXmiLoader.loadConceptFromPlugin(de.dlr.sc.virsat.model.extension.cefx.Activator.PLUGIN_ID + "/concept/concept.xmi");
		
		try {
			closeWelcomeScreen();
			waitForEditingDomainAndUiThread();
			openCefxPerspective();
			waitForEditingDomainAndUiThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method closes the initial Welcome Screen
	 * @throws InterruptedException by Thread.sleep
	 */
	protected void closeWelcomeScreen() throws InterruptedException {
		for (SWTBotView view : bot.views()) {
			System.out.println(view.getTitle());
			if (view.getTitle().equals("Welcome")) {
				Thread.sleep(2000);
				view.close();
			}
		}
	}
	
	/**
	 * This method opens the virtual satellite cef-x perspective
	 * @throws InterruptedException by Thread.sleep
	 */
	protected void openCefxPerspective() throws InterruptedException {
		Thread.sleep(2000);
		bot.menu("Window").contextMenu("Perspective").contextMenu("Open Perspective").contextMenu("Other...").click().setFocus();
		bot.table().select("CEF X");
		Thread.sleep(2000);
		bot.button("Open").click();
		Thread.sleep(2000);
	}
	
	/**
	 * This method retrieves the AutoCheckout project name
	 */
	protected void getAutoCheckoutProject() {
        SWTBotView projectExplorer = bot.viewByTitle("Project Explorer");
        SWTBotTree tree = projectExplorer.bot().tree();
        SWTBotTreeItem[] projects = tree.getAllItems();
        System.err.println("Open project: " + projects[0].getText());
        projectName = projects[0].getText();
	}
	
	/**
	 * This method execute the loadTest
	 */
	protected void executeLoadTest() {
		try {
			productNavigator("" + loop);
			configurationNavigator("" + loop);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void productNavigator(String loop) throws InterruptedException {
		
		// set focus on product navigator
		Thread.sleep(2000);
		bot.viewByTitle("Product Navigator").setFocus();
		waitForEditingDomainAndUiThread();
		
		// open repository 
		Thread.sleep(2000);
		SWTBotTreeItem repositoryItem = bot.tree().expandNode(projectName, "Repository");
		waitForEditingDomainAndUiThread();
		
		// add product tree and rename it
		Thread.sleep(2000);
		SWTBotTreeItem ptEquipmentLibrary = addElement(ProductTree.class, conceptPs, repositoryItem);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ptEquipmentLibrary, "Equipment_Library_To_Use" + "_" + loop);
		
		// add some ProductTreeDomains and rename them
		Thread.sleep(2000);
		SWTBotTreeItem ptdAOCS = addElement(ProductTreeDomain.class, conceptPs, ptEquipmentLibrary);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ptdAOCS, "AOCS" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem ptdPower = addElement(ProductTreeDomain.class, conceptPs, ptEquipmentLibrary);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ptdPower, "Power" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem ptdConOps = addElement(ProductTreeDomain.class, conceptPs, ptEquipmentLibrary);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ptdConOps, "ConOps" + "_" + loop);
		
		// add some ElementDefinition and rename them
		Thread.sleep(2000);
		SWTBotTreeItem edReactionWheel = addElement(ElementDefinition.class, conceptPs, ptdAOCS);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(edReactionWheel, "ReactionWheel" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem edCamera = addElement(ElementDefinition.class, conceptPs, ptdAOCS);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(edCamera, "Camera" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem edBattery = addElement(ElementDefinition.class, conceptPs, ptdPower);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(edBattery, "Battery" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem edConOps = addElement(ElementDefinition.class, conceptPs, ptdConOps);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(edConOps, "ConOps" + "_" + loop);
		
		// add some SystemMode and rename them
		Thread.sleep(2000);
		SWTBotTreeItem smScience = addElement(SystemMode.class, conceptCEFXExtension, edConOps);
		Thread.sleep(1000);
		rename(smScience, "Science" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem smLEOP = addElement(SystemMode.class, conceptCEFXExtension, edConOps);
		Thread.sleep(1000);
		rename(smLEOP, "LEOP" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem smRecharge = addElement(SystemMode.class, conceptCEFXExtension, edConOps);
		Thread.sleep(1000);
		rename(smRecharge, "Recharge" + "_" + loop);

		// activate some parameters
		/*Thread.sleep(2000);
		setParameters(edReactionWheel, "Equipment Parameters");
		
		Thread.sleep(2000);
		setParameters(edReactionWheel, "Equipment Mass Parameters");
		
		Thread.sleep(2000);
		setParameters(edReactionWheel, "Equipment Power Parameters");*/
		
		// save and close all editors
		Thread.sleep(1000);
		save();
		Thread.sleep(2000);
		close();
		
		// add items that we want drag and drop to the configuration tree
		DragAndDroppedItem dragAndDroppedReactionWheel = new DragAndDroppedItem(edReactionWheel, 3, ptdAOCS);
		addDragAndDroppedItemToList(dragAndDroppedReactionWheel);
		DragAndDroppedItem dragAndDroppedConOps = new DragAndDroppedItem(edConOps, 1, ptdConOps);
		addDragAndDroppedItemToList(dragAndDroppedConOps);
	}
	
	protected void configurationNavigator(String loop) throws InterruptedException {		
		
		// set focus on product navigator
		Thread.sleep(2000);
		bot.viewByTitle("Configuration Navigator").setFocus();
		waitForEditingDomainAndUiThread();
		
		// open repository 
		Thread.sleep(2000);
		SWTBotTreeItem repositoryItem = bot.tree(1).expandNode(projectName, "Repository");
		waitForEditingDomainAndUiThread();
		
		// add configuration tree and rename it
		Thread.sleep(2000);
		SWTBotTreeItem ctMySatellite = addElement(ConfigurationTree.class, conceptPs, repositoryItem);
		waitForEditingDomainAndUiThread();
		
		Thread.sleep(1000);
		rename(ctMySatellite, "MySatellite" + "_" + loop);
		waitForEditingDomainAndUiThread();
		
		// add some main ElementConfiguration and rename them
		Thread.sleep(2000);
		SWTBotTreeItem ecAOCS = addElement(ElementConfiguration.class, conceptPs, ctMySatellite);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ecAOCS, "AOCS" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem ecPower = addElement(ElementConfiguration.class, conceptPs, ctMySatellite);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ecPower, "Power" + "_" + loop);
		
		Thread.sleep(2000);
		SWTBotTreeItem ecConOps = addElement(ElementConfiguration.class, conceptPs, ctMySatellite);
		waitForEditingDomainAndUiThread();
		Thread.sleep(1000);
		rename(ecConOps, "ConOps" + "_" + loop);
		
		
		
		// drop dragged items from productTree into configurationTree
		dragAndDropItem(ecAOCS);
		dragAndDropItem(ecConOps);
		
		// save and close all editors
		Thread.sleep(1000);
		save();
		Thread.sleep(2000);
		close();
	}
	
	protected void addDragAndDroppedItemToList(DragAndDroppedItem item) {
		dragAndDroppedItemList.add(item);
	}
	
	protected void dragAndDropItem(SWTBotTreeItem parentDroppedItem) throws InterruptedException {
		for(DragAndDroppedItem item: dragAndDroppedItemList) {
			String parentDraggedItem = item.getParentItem().getText();
			String parentDraggedItemName = parentDraggedItem.substring(parentDraggedItem.lastIndexOf(": ") + 2);
			String parentDroppedItemName = parentDroppedItem.getText().substring(parentDroppedItem.getText().lastIndexOf(": ") + 2);
			
			if(parentDroppedItemName.equals(parentDraggedItemName)) {
				
				Thread.sleep(1000);
				parentDroppedItem.expand();
				waitForEditingDomainAndUiThread();
				
				for(int i=0; i<item.getNumberOfTimesToBeDragAndDropped(); i++) {
					item.getItem().select().dragAndDrop(parentDroppedItem);
					Thread.sleep(2000);
					waitForEditingDomainAndUiThread();
				}
			}
		}
	}
	
	/**
	 * renames an item
	 * @param item the item to be renamed
	 * @param newName the new name
	 */
	protected void setParameters(SWTBotTreeItem item, String parametersType) {
		openEditor(item);
		bot.checkBox(parametersType).click();
		waitForEditingDomainAndUiThread(); 
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds some  elements
	 * @param clazz the class of the element to be added
	 * @param concept the concept of the elements
	 * @param parentItem the place to add the new element
	 * @return the newly added element
	 */
	protected SWTBotTreeItem addElement(Class<?> clazz, Concept concept, SWTBotTreeItem parentItem) {
		System.out.println(clazz.getSimpleName());
		
		IConceptTypeDefinition ctd = ActiveConceptHelper.getStructuralElement(concept, clazz.getSimpleName());
		if (ctd == null) {
			ctd = ActiveConceptHelper.getCategory(concept, clazz.getSimpleName());
		} 
		waitForEditingDomainAndUiThread();
		String menuName = concept.getDisplayName();
		if (menuName == null) {
			menuName = concept.getName();
		}
		final String finalMenuName = menuName;
		final IConceptTypeDefinition finalCtd = ctd;
		buildCounter.executeInterlocked(() -> {
			parentItem.contextMenu().menu(finalMenuName).menu("Add " + finalCtd.getName()).click();
		});
		
		if(ctd.getName().equals("ProductTree")) {
			bot.tree().expandNode(projectName, "Repository");
			waitForEditingDomainAndUiThread();
		}
		if(ctd.getName().equals("ConfigurationTree")) {
			bot.tree(1).expandNode(projectName, "Repository");
			waitForEditingDomainAndUiThread();
		}
		String shortName = DVLMItemNaming.getAbbreviation((IQualifiedName) ctd, "");
		String newItemName = shortName + ": " + ctd.getName(); 
		SWTBotTreeItem newItem = parentItem.getNode(newItemName);
		return newItem;
	}
	
	/**
	 * This method runs a log command on the UI thread in synced/blocking mode. The UI Thread needs to have finished before
	 * the code can return from here. The method also checks if the queue of notifications in the Editing Domain is empty
	 * @throws InterruptedException 
	 */
	protected static void waitForEditingDomainAndUiThread() {
		
		// Start waiting for the Editing Domain
		VirSatTransactionalEditingDomain.waitForFiringOfAccumulatedResourceChangeEvents();
		
		// Wait a little time, so we give other UI threads / runnables the chance to get started or queued in between
		try {
			Thread.sleep(SWTBOT_GENERAL_WAIT_TIME);
		} catch (InterruptedException e) {
			//Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.getPluginId(), "SWTBot Test: Thread Interrupted", e));
		}

		// Now throw in a runnable to the queue but execute it blocking, thus this method will only leave in case
		// all other runnables queued before have been executed.
		WaitForRunnable defaultDisplayWaitFor = new WaitForRunnable();
		Display.getDefault().asyncExec(defaultDisplayWaitFor);
		defaultDisplayWaitFor.waitForExecution();

		// Add some grace time just for the res
		try {
			Thread.sleep(SWTBOT_GENERAL_WAIT_TIME);
		} catch (InterruptedException e) {
			//Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.getPluginId(), "SWTBot Test: Thread Interrupted", e));
		}
	}
	
	/**
	 * A Runnable lock to make sure that the display thread executed all messages
	 */
	static class WaitForRunnable implements Runnable {
		
		protected static final int SWTBOT_GENERAL_WAIT_TIME = 50;
		private Boolean gotExecuted = false;
		
		@Override
		public synchronized void run() {
			gotExecuted = true;
			notify();
			//Activator.getDefault().getLog().log(new Status(Status.INFO, Activator.getPluginId(), "Wait For Runnable UI Thread: " + Thread.currentThread()));
		}
		
		/**
		 * Call this method to make sure the runnable got executed
		 * THis method blocks until the runnable got called.
		 */
		synchronized void waitForExecution() {
			while (!gotExecuted) {
				try {
					wait(SWTBOT_GENERAL_WAIT_TIME);
				} catch (InterruptedException e) {
					//Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.getPluginId(), "Could not go to sleep Thread: " + Thread.currentThread()));
				}
			}
			//Activator.getDefault().getLog().log(new Status(Status.INFO, Activator.getPluginId(), "Runnable got Executed Thread: " + Thread.currentThread()));
		}
	}
	
	/**
	 * This class is used to interlock an execution of code with the Workspace Builders.
	 * This is useful when e.g. saving editors and making sure, everything in the files
	 * and UI is updated.
	 */
	protected static class WorkspaceBuilderInterlockedExecution {
		
		protected static final int SWTBOT_GENERAL_WAIT_TIME = 50; 

		/**
		 * The runnable in this method is interlocked with the execution of the workspace builders.
		 * First the method will join all scheduled workspace builders and wait for the UI to refresh.
		 * Then it will execute the runnable and wait again to join builders and UI refresh.
		 * @param runnable the runnable to be executed
		 */
		public void executeInterlocked(Runnable runnable) {
			try {
				// Now wait that already scheduled jobs are definitely done and wait for the ED and UI thread to finalize
				//Activator.getDefault().getLog().log(new Status(Status.OK, Activator.getPluginId(), "ASwtBotTest.InterlockedBuildCounter: Wait for jobs to be done before execution and counting"));
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				waitForEditingDomainAndUiThread();
	
				// Now execute the runnable and wait for some time to allow for builders to be scheduled
				//Activator.getDefault().getLog().log(new Status(Status.OK, Activator.getPluginId(), "ASwtBotTest.InterlockedBuildCounter: About to execute interlocked runnable"));
				runnable.run();
				Thread.sleep(SWTBOT_GENERAL_WAIT_TIME);
				
				// Now wait that all scheduled builders are done and update the UI
				//Activator.getDefault().getLog().log(new Status(Status.OK, Activator.getPluginId(), "ASwtBotTest.InterlockedBuildCounter: Wait for jobs to be done after execution and counting"));
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				waitForEditingDomainAndUiThread();
				
				//Activator.getDefault().getLog().log(new Status(Status.OK, Activator.getPluginId(), "ASwtBotTest.InterlockedBuildCounter: Counted all VirSat Builders and waited for all other builders"));
			} catch (InterruptedException e) {
				//Activator.getDefault().getLog().log(new Status(Status.WARNING, Activator.getPluginId(), "ASwtBotTest.InterlockedBuildCounter: Thread got interupted", e));
			}
		}
	}
	
	/**
	 * This method saves the editors
	 */
	protected void save() {
		bot.saveAllEditors();
		waitForEditingDomainAndUiThread();
	}
	
	/**
	 * This method closes the editors
	 */
	protected void close() {
		bot.closeAllEditors();
		waitForEditingDomainAndUiThread();
	}
	
	/**
	 * renames an item
	 * @param item the item to be renamed
	 * @param newName the new name
	 */
	protected void rename(SWTBotTreeItem item, String newName) {
		openEditor(item);
		bot.textWithLabel("Name").setText(newName);
		waitForEditingDomainAndUiThread(); 
	}
	
	/**
	 * Opens an editor
	 * @param item the name of the element
	 * @return SWTBotTreeItem the opened item
	 */
	protected SWTBotTreeItem openEditor(SWTBotTreeItem item) {
		SWTBotTreeItem newItem = item.doubleClick();
		waitForEditor(item);
		waitForEditingDomainAndUiThread();
		return newItem;
	}
	
	/**
	 * Waits for the editor of the passed item to open.
	 * The editor is identified using the name of the item,
	 * so it can't distinguish between editors of items with the same name!
	 * @param item the item whose editor we are waiting for
	 */
	protected void waitForEditor(SWTBotTreeItem item) {
		String label = item.getText();
		Matcher<IEditorReference> matcher = withPartName(StringStartsWith.startsWith(label + " -> "));
		bot.waitUntil(Conditions.waitForEditor(matcher));
	}
}