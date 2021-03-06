/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cef.ui.masssummary;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.swt.ChartComposite;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.dlr.sc.virsat.model.concept.types.util.BeanCategoryAssignmentHelper;
import de.dlr.sc.virsat.model.dvlm.structural.StructuralElementInstance;
import de.dlr.sc.virsat.model.extension.cef.model.Equipment;
import de.dlr.sc.virsat.model.extension.cef.model.EquipmentMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystem;
import de.dlr.sc.virsat.model.extension.cef.model.SubSystemMassParameters;
import de.dlr.sc.virsat.model.extension.cef.model.SystemMassParameters;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain;
import de.dlr.sc.virsat.project.editingDomain.VirSatTransactionalEditingDomain.IResourceEventListener;

/**
 * View providing a mass summary
 * @author muel_s8
 *
 */

public class MassSummaryView extends ViewPart {

	public static final String VIEW_ID = "de.dlr.sc.virsat.model.extension.cef.ui.masssummary.massSummaryView";
	
	private static final String TITLE_TEXT_START_TOKEN =  "Mass Summary";
	private static final String LABEL_START = "Mass breakdown of: ";

	private static final int CHART_HEIGHT = 400;
	private static final int CHART_WIDTH = 600;
	
	ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection sel) {
			if (!(sel instanceof IStructuredSelection)) {
				return;
			}

			IStructuredSelection ss = (IStructuredSelection) sel;
			Object o = ss.getFirstElement();

			if (o instanceof StructuralElementInstance) {
				setInput((StructuralElementInstance) o);
				update();
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

	private ScrolledForm form;
	private Label seiLabel;
	private List<AUiSnippetTable> uiSnippets = new ArrayList<>();
	private JFreeChart chart;
	private ChartComposite chartComposite;
	
	private StructuralElementInstance sei;
	private VirSatTransactionalEditingDomain domain;
	private URI resourceURI;
	
	private boolean isDisposed;
	
	@Override
	public void createPartControl(Composite parent) {
		Display display = parent.getDisplay();
		FormToolkit toolkit = new FormToolkit(display);
		form = toolkit.createScrolledForm(parent);
		
		// Add some heading style to the thing
		toolkit.decorateFormHeading(form.getForm());
		form.setImage(getTitleImage());     
		form.setText(TITLE_TEXT_START_TOKEN);	
		
		// Basic grid layout with 1 element per row
		GridLayout layout = new GridLayout(1, true);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		form.getBody().setLayout(layout);
		
		seiLabel = toolkit.createLabel(form.getBody(), LABEL_START, SWT.NONE);
		
		// Create the two tables to display mass summaries for the individual elements
		// and also the system summary
		AUiSnippetTable elements = new UiSnippetElements();
		elements.createSwt(toolkit, form.getBody());
		uiSnippets.add(elements);
		
		AUiSnippetTable system = new UiSnippetSystem();
		system.createSwt(toolkit, form.getBody());
		uiSnippets.add(system);
		
		//make a chart
		chart = createChart(createDataset());
		chart.setBorderVisible(false);
		
		// the chart need to be gridified
		GridData chartGridData = new GridData(SWT.LEFT, SWT.FILL, true, false); 
		chartGridData.heightHint = CHART_HEIGHT;
		chartGridData.widthHint = CHART_WIDTH;
		
		chartComposite = new ChartComposite(form.getBody(), SWT.NONE, chart, true);
		chartComposite.setLayoutData(chartGridData);
		
		form.reflow(true);
		
		isDisposed = false;
		getSite().getPage().addSelectionListener(listener);
		VirSatTransactionalEditingDomain.addResourceEventListener(eventListener);
	}
	
	/**
	 * Creates the pie chart
	 * @param dataset data for the pie chart
	 * @return a configured pie chart using the passed data set
	 */
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(null, // chart title
				  dataset, // data
				  false, // include legend
				  false, false);

		//CHECKSTYLE:OFF
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		
		plot.setNoDataMessage("No data available");
		plot.setCircular(true);
		plot.setLabelGap(0.02);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(false);
		plot.setLabelOutlinePaint(Color.DARK_GRAY);
		plot.setLabelBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setSectionPaint("Discipline1", new Color(204, 204, 255));
		plot.setSectionPaint("Discipline2", new Color(153, 153, 255));
		plot.setSectionPaint("Discipline3", new Color(102, 102, 255));
		plot.setSectionPaint("Discipline4", new Color(51, 51, 255));
		plot.setSectionPaint("Five", new Color(0, 0, 255));
		plot.setSectionPaint("Six", new Color(153, 153, 204));
		plot.setLabelShadowPaint(Color.WHITE);
		chart.setAntiAlias(true);
		chart.setBorderVisible(true);
		chart.setBackgroundPaint(null);
		
		//set the title
		TextTitle myChartTitel = new TextTitle("Contribution to total mass in [%]", new Font("SansSerif", Font.BOLD, 12));
		chart.setTitle(myChartTitel);
		//CHECKSTYLE:ON
		
		return chart;
	}

	/**
	 * Create a dataset for a pie chart
	 * @return a dataset for a pie chart, empty if no system is selected
	 */
	
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		// Check if we have a valid system that has all the necessary parameters attached
		// required to actually do a mass summary
		
		if (sei == null) {
			return dataset;
		}
		
		BeanCategoryAssignmentHelper beanCaHelper = new BeanCategoryAssignmentHelper();
		List<SystemMassParameters> systemMassParametersList = beanCaHelper.getAllBeanCategories(sei, SystemMassParameters.class);
		double massWithMarginSystemTotal = 0;
		
		if (systemMassParametersList.size() == 0) {
			// if we dont have a system mass, check for a subsystem mass
			
			List<SubSystemMassParameters> subSysMassParametersList = beanCaHelper.getAllBeanCategories(sei, SubSystemMassParameters.class);
			if (subSysMassParametersList.size() == 0) {
				// if we dont have subsystem mass parameters, check for equipment mass parameters
	
				List<EquipmentMassParameters> equipMassParametersList = beanCaHelper.getAllBeanCategories(sei, EquipmentMassParameters.class);
				
				if (equipMassParametersList.size() == 0) {
					// if we dont even have equipment mass parameters then there is nothing to calculate
					return dataset;	
				} else {
					for (EquipmentMassParameters equipMassParameters : equipMassParametersList) {
						if (equipMassParameters.getMassTotalWithMargin().isSetDefaultValue()) {
							massWithMarginSystemTotal += equipMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
						}
					}
				}
				
			} else {
				// else we can compute a contribution
				SubSystemMassParameters subSystemMassParameters = subSysMassParametersList.get(0);
				if (!subSystemMassParameters.getMassTotalWithMargin().isSetDefaultValue()) {
					return dataset;
				}	
							
				massWithMarginSystemTotal = massWithMarginSystemTotal + subSystemMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
			}
		} else {
			// if we have a system mass
			SystemMassParameters systemMassParameters = systemMassParametersList.get(0);
			if (!systemMassParameters.getMassTotalWithMargin().isSetDefaultValue()) {
				return dataset;
			}
			
			massWithMarginSystemTotal = systemMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
		}
		
		List<StructuralElementInstance> listSei = sei.getChildren();
		
		// Now check the children
		for (StructuralElementInstance sei : listSei) {	
			// Check if they actually have well defined mass parameters
			if (sei.getType().getName().matches(SubSystem.class.getSimpleName())) {
				List<SubSystemMassParameters> massParametersList = beanCaHelper.getAllBeanCategories(sei, SubSystemMassParameters.class);

				if (massParametersList.size() == 0) {
					continue;
				}

				SubSystemMassParameters massParameters = massParametersList.get(0);

				if (!massParameters.getMassTotalWithMargin().isSetDefaultValue()) {
					continue;
				}

				// Calculate the individual contributions
				double massWithMarginTotal = massParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
				double result = massWithMarginTotal / massWithMarginSystemTotal;
				dataset.setValue(sei.getName(), result);
			} else if (sei.getType().getName().matches(Equipment.class.getSimpleName())) {
				// Check if they actually have well defined mass parameters
				List<EquipmentMassParameters> equipMassParametersList = beanCaHelper.getAllBeanCategories(sei, EquipmentMassParameters.class);

				if (equipMassParametersList.size() == 0) {
					continue;
				}

				double massWithMarginTotal = 0;
				for (EquipmentMassParameters equipMassParameters : equipMassParametersList) {
					if (equipMassParameters.getMassTotalWithMargin().isSetDefaultValue()) {
						massWithMarginTotal += equipMassParameters.getMassTotalWithMargin().getDefaultValueBean().getValueToBaseUnit();
					}
				}

				// Calculate the individual contributions
				double result = massWithMarginTotal / massWithMarginSystemTotal;
				dataset.setValue(sei.getName(), result);
			}		
		}
			
		return dataset;
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
		form.setFocus();
	}
	
	/**
	 * Set the input system
	 * @param sei the structural element instance of the system
	 */
	public void setInput(StructuralElementInstance sei) {
		this.sei = sei;
		domain = (VirSatTransactionalEditingDomain) VirSatTransactionalEditingDomain.getEditingDomainFor(sei);
		resourceURI = sei.eResource().getURI();
		
		update();
	}
	
	/**
	 * Forced update of tables and chart	
	 */
	
	public void update() {
		if (isDisposed) {
			return;
		}
		
		if (sei != null) {
			seiLabel.setText(LABEL_START + sei.getName());
		} else {
			seiLabel.setText(LABEL_START);
		}
	
		uiSnippets.forEach((snippet) -> {
			snippet.setDataBinding(domain, sei);
			snippet.update();
		});
		
		updateSummaryChart();
		
		form.getBody().layout();
	}
	
	/**
	 * This method updates the bar chart with new data from the selected system
	 */
	private void updateSummaryChart() {
		// First get the plot and update the Data Range
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setDataset(createDataset());
		
		chart.fireChartChanged();
		chartComposite.forceRedraw();
	}
	
	/**
	 * This is the method called to load a resource into the editing domain's resource set based on the editor's input.
	 */
	public void reloadModel() {
		if (sei != null) {
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
