/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.cefx.model;

// *****************************************************************
// * Import Statements
// *****************************************************************
import de.dlr.sc.virsat.model.concept.list.IBeanList;
import de.dlr.sc.virsat.model.concept.list.TypeSafeReferencePropertyBeanList;
import de.dlr.sc.virsat.model.concept.list.TypeSafeReferencePropertyInstanceList;
import de.dlr.sc.virsat.model.concept.types.category.IBeanCategoryAssignment;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyReference;
import de.dlr.sc.virsat.model.concept.types.property.BeanPropertyResource;
import de.dlr.sc.virsat.model.dvlm.categories.Category;
import de.dlr.sc.virsat.model.dvlm.categories.CategoryAssignment;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ArrayInstance;
import de.dlr.sc.virsat.model.dvlm.categories.propertyinstances.ResourcePropertyInstance;
import de.dlr.sc.virsat.model.dvlm.categories.util.CategoryInstantiator;
import de.dlr.sc.virsat.model.dvlm.concepts.Concept;
import de.dlr.sc.virsat.model.dvlm.concepts.util.ActiveConceptHelper;
import de.dlr.sc.virsat.model.ext.core.model.GenericCategory;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;


// *****************************************************************
// * Class Declaration
// *****************************************************************

/**
 * Auto Generated Abstract Generator Gap Class
 * 
 * Don't Manually modify this class
 * 
 * 
 * 
 */	
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AExcelCalculation extends GenericCategory implements IBeanCategoryAssignment {

	public static final String FULL_QUALIFIED_CATEGORY_NAME = "de.dlr.sc.virsat.model.extension.cefx.ExcelCalculation";
	
	/**
 	* Call this method to get the full qualified name of the underlying category
 	* @return The FQN of the category as String
 	*/
	public String getFullQualifiedCategoryName() {
		return FULL_QUALIFIED_CATEGORY_NAME;
	}
	
	// property name constants
	public static final String PROPERTY_EXCELFILE = "excelFile";
	public static final String PROPERTY_FROMVIRSAT2EXCEL = "fromVirSat2Excel";
	public static final String PROPERTY_FROMEXCEL2VIRSAT = "fromExcel2VirSat";
	
	
	
	// *****************************************************************
	// * Class Constructors
	// *****************************************************************
	
	public AExcelCalculation() {
	}
	
	public AExcelCalculation(Concept concept) {
		Category categoryFromActiveCategories = ActiveConceptHelper.getCategory(concept, "ExcelCalculation");
		CategoryAssignment categoryAssignement = new CategoryInstantiator().generateInstance(categoryFromActiveCategories, "ExcelCalculation");
		setTypeInstance(categoryAssignement);
	}
	
	public AExcelCalculation(CategoryAssignment categoryAssignement) {
		setTypeInstance(categoryAssignement);
	}
	
	
	// *****************************************************************
	// * Attribute: excelFile
	// *****************************************************************
	private BeanPropertyResource excelFile = new BeanPropertyResource();
	
	private void safeAccessExcelFile() {
		if (excelFile.getTypeInstance() == null) {
			excelFile.setTypeInstance((ResourcePropertyInstance) helper.getPropertyInstance("excelFile"));
		}
	}
	
	public Command setExcelFile(EditingDomain ed, URI value) {
		safeAccessExcelFile();
		return this.excelFile.setValue(ed, value);
	}
	
	public void setExcelFile(URI value) {
		safeAccessExcelFile();
		this.excelFile.setValue(value);
	}
	
	public URI getExcelFile() {
		safeAccessExcelFile();
		return excelFile.getValue();
	}
	
	@XmlElement
	public BeanPropertyResource getExcelFileBean() {
		safeAccessExcelFile();
		return excelFile;
	}
	
	// *****************************************************************
	// * Array Attribute: fromVirSat2Excel
	// *****************************************************************
		private IBeanList<Parameter> fromVirSat2Excel = new TypeSafeReferencePropertyInstanceList<>(Parameter.class);
	
		private void safeAccessFromVirSat2Excel() {
			if (fromVirSat2Excel.getArrayInstance() == null) {
				fromVirSat2Excel.setArrayInstance((ArrayInstance) helper.getPropertyInstance("fromVirSat2Excel"));
			}
		}
	
		public IBeanList<Parameter> getFromVirSat2Excel() {
			safeAccessFromVirSat2Excel();
			return fromVirSat2Excel;
		}
		
		private IBeanList<BeanPropertyReference<Parameter>> fromVirSat2ExcelBean = new TypeSafeReferencePropertyBeanList<>();
		
		private void safeAccessFromVirSat2ExcelBean() {
			if (fromVirSat2ExcelBean.getArrayInstance() == null) {
				fromVirSat2ExcelBean.setArrayInstance((ArrayInstance) helper.getPropertyInstance("fromVirSat2Excel"));
			}
		}
		
		@XmlElement
		public IBeanList<BeanPropertyReference<Parameter>> getFromVirSat2ExcelBean() {
			safeAccessFromVirSat2ExcelBean();
			return fromVirSat2ExcelBean;
		}
	
	// *****************************************************************
	// * Array Attribute: fromExcel2VirSat
	// *****************************************************************
		private IBeanList<Parameter> fromExcel2VirSat = new TypeSafeReferencePropertyInstanceList<>(Parameter.class);
	
		private void safeAccessFromExcel2VirSat() {
			if (fromExcel2VirSat.getArrayInstance() == null) {
				fromExcel2VirSat.setArrayInstance((ArrayInstance) helper.getPropertyInstance("fromExcel2VirSat"));
			}
		}
	
		public IBeanList<Parameter> getFromExcel2VirSat() {
			safeAccessFromExcel2VirSat();
			return fromExcel2VirSat;
		}
		
		private IBeanList<BeanPropertyReference<Parameter>> fromExcel2VirSatBean = new TypeSafeReferencePropertyBeanList<>();
		
		private void safeAccessFromExcel2VirSatBean() {
			if (fromExcel2VirSatBean.getArrayInstance() == null) {
				fromExcel2VirSatBean.setArrayInstance((ArrayInstance) helper.getPropertyInstance("fromExcel2VirSat"));
			}
		}
		
		@XmlElement
		public IBeanList<BeanPropertyReference<Parameter>> getFromExcel2VirSatBean() {
			safeAccessFromExcel2VirSatBean();
			return fromExcel2VirSatBean;
		}
	
	
}
