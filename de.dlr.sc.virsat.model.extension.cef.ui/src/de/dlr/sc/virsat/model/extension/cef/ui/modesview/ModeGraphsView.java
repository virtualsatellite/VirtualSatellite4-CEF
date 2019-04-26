/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.modesview;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import de.dlr.sc.virsat.cef.calculation.modes.ModeVectorResult;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ComposedPropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.qudv.AUnit;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.dvlm.types.impl.VirSatUuid;
import de.dlr.sc.virsat.model.extension.cef.model.Parameter;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMode;
import de.dlr.sc.virsat.model.extension.cef.model.Value;
import de.dlr.sc.virsat.model.extension.cef.ui.Activator;
import de.dlr.sc.virsat.model.extension.cef.util.CefModeHelper;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain.IResourceEventListener;

/**
 * UI giving the user a view on parameter values during different modes.
 * @author muel_s8
 *
 */

public class ModeGraphsView extends ViewPart {

	public static final String VIEW_ID = "de.dlr.sc.virsat.extension.cef.ui.modesview.modeGraphsView";
	
	private static final String TITLE_TEXT_START_TOKEN =  "Mode Overview Graphs: ";
	private static final String PARAMETER_MODES_GRAPH_NAME = "Parameter Mode Values";
	private static final String PARAMETER_MODES_XAXIS_NAME = "Modes";
	private static final String PARAMETER_MODES_CONTRIBUTION_NAME = "Subsystem contribution: ";
	private static final String PARAMETER = "Parameter";
	private static final String SYSTEM_MODE = "SystemMode";
	
	private ChartComposite barChartComposite;
	private ChartComposite pieChartComposite;
	private JFreeChart barChart;
	private JFreeChart pieChart;
	
	private ScrolledForm form;
	
	private Parameter param;
	private SystemMode mode;
	
	private VirSatUuid paramUuid;
	private VirSatUuid modeUuid;
	private VirSatTransactionalEditingDomain domain;
	private URI paramResourceURI; 
	private URI modeResourceURI; 
	
	private boolean isDisposed;
	
	
	ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection sel) {
			if (!(sel instanceof IStructuredSelection)) {
				return;
			}

			IStructuredSelection ss = (IStructuredSelection) sel;
			Object o = ss.getFirstElement();
			
			if (o instanceof ComposedPropertyInstance) {
				ComposedPropertyInstance cpi = (ComposedPropertyInstance) o;
				o = cpi.getTypeInstance();
			}
			
			if (o instanceof CategoryAssignment) {
				CategoryAssignment ca = (CategoryAssignment) o;
				if (ca.getType().getName().equals(PARAMETER)) {
					setParameter(new Parameter(ca));
				} else if (ca.getType().getName().equals(SYSTEM_MODE)) {
					setSystemMode(new SystemMode(ca));
				}
			}
		}
	};
	
	private IResourceEventListener eventListener = (Set<Resource> newResources, int event) -> {
		Display.getDefault().asyncExec(() -> {
			if (event == VirSatTransactionalEditingDomain.EVENT_RELOAD) {
				reloadModel();
			}
			
			update();
		});
	};
	
	@Override
	public void createPartControl(Composite parent) {
		Display display = parent.getDisplay();
		FormToolkit toolkit = new FormToolkit(display);
		form = toolkit.createScrolledForm(parent);
		
		// Add some heading style to the thing
		toolkit.decorateFormHeading(form.getForm());
		form.setImage(getTitleImage());     
		form.setText(TITLE_TEXT_START_TOKEN);
		
		// Set the Layout for the ScrolledForm
		GridLayout layout = new GridLayout(2, true);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		form.getBody().setLayout(layout);
    	
		barChart = createParameterChart(createParameterDataset());
		barChartComposite = new ChartComposite(form.getBody(), SWT.NONE, barChart, true);
		barChartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pieChart = createContributerChart(createContributorDataset());
		pieChartComposite = new ChartComposite(form.getBody(), SWT.NONE, pieChart, true);
		pieChartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		form.reflow(true);
		
		barChartComposite.addChartMouseListener(new ChartMouseListener() {
			@Override
			public void chartMouseClicked(ChartMouseEvent event) {
				if (param != null) {
					ChartEntity entity = event.getEntity();
					if (entity instanceof CategoryItemEntity) {
						onClickRow((CategoryItemEntity) entity);
					}
				}
			}
			
			@Override
			public void chartMouseMoved(ChartMouseEvent event) {

			}
		});
		
		isDisposed = false;
		getSite().getPage().addSelectionListener(listener);
		VirSatTransactionalEditingDomain.addResourceEventListener(eventListener);
	}

	/**
	 * Reaction to the user clicking on a row in the chart
	 * @param ciEntity the clicked on chart entity
	 */
	protected void onClickRow(CategoryItemEntity ciEntity) {
		String rowKey = (String) ciEntity.getRowKey();
		
		SystemMode mode = null;
		
		if (!rowKey.equals("Default")) {
			// If its not the default mode, search for the fitting mode through all system wide defined modes
			CefModeHelper cefModeHelper = new CefModeHelper();
			List<SystemMode> modes = cefModeHelper.getAllModes(param);
			for (SystemMode systemMode : modes) {
				if (systemMode.getName().equals(rowKey)) {
					mode = systemMode;
				}
			}
		}
		
		setSystemMode(mode);
	}

	@Override
	public void dispose() {
		isDisposed = true;
		getSite().getPage().removeSelectionListener(listener);
		VirSatTransactionalEditingDomain.removeResourceEventListener(eventListener);
		super.dispose();
	}
	
	@Override
	public void setFocus() {

	}
	
	/**
	 * This Method creates a compatible dataset to display the contributions of a calculation
	 * obeying a selected mode.  
	 * @return compatible dataset for pie charts
	 */
	private PieDataset createContributorDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		//special handling for "default" mode
		if (param != null) {
			CefModeHelper cefModeHelper = new CefModeHelper();
			ContributionHelper contrHelper = new ContributionHelper(param);
			List<Parameter> sourceParameters = contrHelper.getSourceParameters();
			for (Parameter sourceParameter : sourceParameters) {
				StructuralElementInstance sei = (StructuralElementInstance) CategoryAssignmentHelper.getContainerFor(sourceParameter.getATypeInstance());
				double value = cefModeHelper.getModeValueOrDefault(sourceParameter, mode);
				if (value != 0) {
					dataset.setValue(" " + sei.getName() + "->" + sourceParameter.getName() + " ", value);
				}
			}
		} 
		return dataset;
	}
	
	/**
	 * This method creates the pie chart showing the contributions of each calculation
	 * @param dataset The dataset with which the pie chart will be started
	 * @return the chart object
	 */
	private JFreeChart createContributerChart(PieDataset dataset) {
	
		JFreeChart chart = ChartFactory.createPieChart(
					  PARAMETER_MODES_CONTRIBUTION_NAME + "Default" + " " + "Mode",
					  dataset, // data
					  true, // include legend
					  true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("Please select a system mode");
		plot.setCircular(true);
		final double LABELGAP = 0.02;
		plot.setLabelGap(LABELGAP);
		return chart;
	}
	
	/**
	 * Creates the Chart based on a dataset
	 * @param dataset The dataset that will be used for the chart
	 * @return the constructed chart
	 */
	private JFreeChart createParameterChart(DefaultCategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createBarChart(
				  PARAMETER_MODES_GRAPH_NAME, // chart title
				  PARAMETER_MODES_XAXIS_NAME, // domain axis label
				  "Value", // range axis label
				  dataset, // data
				  PlotOrientation.VERTICAL, // orientation
				  true, // include legend
				  true, // tooltips?
				  false // URLs?
		);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setNoDataMessage("Please select a parameter");
		
		return chart;
	}
	
	public static final String BARCHART_CATEGORY = "Modes :";
	
	/**
	 * Creates the dataset to display a parameter in respect to its modes
	 * @return compatible dataset for a barchart
	 */
	private DefaultCategoryDataset createParameterDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		if (param != null) {
			CefModeHelper cefModeHelper = new CefModeHelper();
			double defaultValue = param.isSetDefaultValue() ? param.getDefaultValue() : 0;
			dataset.setValue(defaultValue, "Default", "");
			
			//getting parameter values for each mode
			List<SystemMode> modes = cefModeHelper.getAllModes(param);
			for (SystemMode mode : modes) {
				Value modeValue = cefModeHelper.getModeValue(param, mode);
				String name = mode.getName();
				if (modeValue != null) {
					dataset.setValue(modeValue.isSetValue() ? modeValue.getValue() : 0, name, "");
				} else {
					dataset.setValue(defaultValue, name, "");
				}
			}	
		} 
		return dataset;
	}

	/**
	 * This method updates the bar chart with new data from the selected parameter
	 */
	private void updateParameterCharts() {
		// First get the plot and update the Data Range
		CategoryPlot plot = (CategoryPlot) barChart.getPlot();
		plot.setDataset(createParameterDataset());
		
		if (param != null) {
			try {
				// Then look to the Parameter and identify its Unit Symbol
				// And its quantity Kind
				AUnit unit = param.getDefaultValueBean().getTypeInstance().getUnit();
				if (unit != null) {
					String unitSymbol = unit.getSymbol();
					String quantityKind = unit.getQuantityKind().getName();
				
					plot.getRangeAxis().setLabel(quantityKind + " [" + unitSymbol + "]");
				} else {
					plot.getRangeAxis().setLabel("");
				}
			} catch (Exception e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to retrieve propper unit and quantitykind", e));
			}
		}
		
		barChart.fireChartChanged();
		barChartComposite.forceRedraw();
	}

	/**
	 * This method updates the pie chart with information regarding
	 * the selected parameter and mode
	 */
	private void updateContributorCharts() {
		// First get the plot and update the Data Range
		PiePlot plot = (PiePlot) pieChart.getPlot();
		plot.setDataset(createContributorDataset());
		
		String modeName = "Default";
		if (mode != ModeVectorResult.DEFAULT_MODE) {
			modeName = mode.getName();
		}
	
		pieChart.fireChartChanged();
		pieChart.setTitle(PARAMETER_MODES_CONTRIBUTION_NAME + modeName + " Mode");
		pieChartComposite.forceRedraw();
	}
	

	/**
	 * This method just takes care of updating the forms title accrodingly to the selections
	 */
	private void updateFormTitle() {
		String paramName = "No Parameter";
		if (param != null) {
			paramName = param.getName();
		}
		form.setText(TITLE_TEXT_START_TOKEN + paramName);
	}
	
	/**
	 * Update everything
	 */
	public void update() {
		
		if (isDisposed) {
			return;
		}
		
		checkForStaleObjects();
		
		if (param != null) {
			domain = (VirSatTransactionalEditingDomain) VirSatTransactionalEditingDomain.getEditingDomainFor(param.getATypeInstance());
			Resource resource = param.getATypeInstance().eResource();
			paramResourceURI = resource.getURI();
			paramUuid = param.getATypeInstance().getUuid();
		}
		
		if (mode != null) {
			Resource resource = mode.getATypeInstance().eResource();
			modeResourceURI = resource.getURI();
			modeUuid = mode.getATypeInstance().getUuid();
		}
		
		updateFormTitle();
		updateParameterCharts();
		updateContributorCharts();
	}
	
	/**
	 * Checks if the selected parameter or mode are stale objects.
	 * If so, the selection is set to nothing (=null)
	 */
	private void checkForStaleObjects() {
		if (param != null && param.getATypeInstance().eResource() == null) {
			param = null;
		}
		
		if (mode != null && mode.getATypeInstance().eResource() == null) {
			mode = null;
		}
	}
	
	/**
	 * set the selected parameter
	 * @param param the parameter to be displayed in the graphs
	 */
	public void setParameter(Parameter param) {
		this.param = param;
		update();
	}
	
	/**
	 * set the selected mode
	 * @param mode the mode to be displayed in the graphs
	 */
	public void setSystemMode(SystemMode mode) {
		this.mode = mode;
		update();
	}
	
	/**
	 * This is the method called to load a resource into the editing domain's resource set based on the editor's input.
	 */
	public void reloadModel() {
		if (domain != null) {
			if (param != null) {
				Resource resource = null;
				try {
					// Load the resource through the editing domain.
					resource = domain.getResourceSet().getResource(paramResourceURI, true);
				} catch (Exception e) {
					resource = domain.getResourceSet().getResource(paramResourceURI, false);
				}
		
				CategoryAssignment ca = (CategoryAssignment) resource.getEObject(paramUuid.toString());
				param = ca != null ? new Parameter(ca) : null;
			}
			
			if (mode != null) {
				Resource resource = null;
				try {
					// Load the resource through the editing domain.
					resource = domain.getResourceSet().getResource(modeResourceURI, true);
				} catch (Exception e) {
					resource = domain.getResourceSet().getResource(modeResourceURI, false);
				}
				
				CategoryAssignment ca = (CategoryAssignment) resource.getEObject(modeUuid.toString());
				mode = ca != null ? new SystemMode(ca) : null;
			}
		}
	}

}
