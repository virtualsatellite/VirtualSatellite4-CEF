
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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IStartup;

import de.dlr.sc.virsat.model.dvlm.Repository;
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

	@Override
	public void earlyStartup() {
		System.out.println("hello world");
		
		beforeLoadTest();
		
		AutoCheckout autoCheckout = new AutoCheckout();
		autoCheckout.performAutocheckout();
		getAutoCheckoutProject();
		
		executeLoadTest();
	}
	
	private void beforeLoadTest() { 
		VirSatEditingDomainClipBoard.INSTANCE.clear();
		VirSatEditingDomainRegistry.INSTANCE.clear();  
		VirSatTransactionalEditingDomain.clearAccumulatedRecourceChangeEvents();

		closeWelcomeScreen();
		waitForEditingDomainAndUiThread();
		//getAutoCheckoutProject();
		waitForEditingDomainAndUiThread();
		openCefxPerspective();
		waitForEditingDomainAndUiThread();
	}
	
	/**
	 * This method closes the initial Welcome Screen
	 */
	protected void closeWelcomeScreen() {
		for (SWTBotView view : bot.views()) {
			System.out.println(view.getTitle());
			if (view.getTitle().equals("Welcome")) {
				view.close();
			}
		}
	}
	
	/**
	 * This method opens the virtual satellite cef-x perspective
	 */
	protected void openCefxPerspective() {
		try {
			bot.menu("Window").contextMenu("Perspective").contextMenu("Open Perspective").contextMenu("Other...").click().setFocus();
			Thread.sleep(3000);
			bot.table().select("CEF X");
			Thread.sleep(3000);
			bot.button("Open").click();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	 * 
	 */
	protected void executeLoadTest() {
		addAllConcepts();
		
	}
	
	/**
	 * Add all concepts
	 * @param projectName the name of the project
	 * @throws  
	 */
	protected void addAllConcepts() {
		waitForEditingDomainAndUiThread();
		bot.viewById("de.dlr.sc.virsat.project.ui.navigator.view").setFocus();
		waitForEditingDomainAndUiThread();
		
		try {
			SWTBotTreeItem projectItem = bot.tree().expandNode(projectName);
			Thread.sleep(3000);
			waitForEditingDomainAndUiThread();
			projectItem.getNode(Repository.class.getSimpleName()).doubleClick();
			Thread.sleep(3000);
			waitForEditingDomainAndUiThread();
			bot.button("Add from Registry").click();
			Thread.sleep(3000);
			bot.button("Select All").click();
			Thread.sleep(3000);
			bot.button("OK").click();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		save();
		waitForEditingDomainAndUiThread();
	}
	
	///////////////////////////////////////////////////////////////////////////
	
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
	 * saves the editors
	 */
	protected void save() {
		bot.saveAllEditors();
		waitForEditingDomainAndUiThread();
	}
}