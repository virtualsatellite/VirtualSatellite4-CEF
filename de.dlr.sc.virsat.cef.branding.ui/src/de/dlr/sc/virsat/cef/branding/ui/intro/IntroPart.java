/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.cef.branding.ui.intro;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;

import de.dlr.sc.virsat.cef.branding.ui.BrandingPlugin;
import de.dlr.sc.virsat.cef.branding.ui.PluginXml;

/**
 * Introduction / Welcome Screen page for VirSat
 */
public class IntroPart implements IIntroPart {
	
	@Override
	public IIntroSite getIntroSite() {
		return null;
	}

	@Override
	public void init(IIntroSite site, IMemento memento)	throws PartInitException {
	}

	@Override
	public void standbyStateChanged(boolean standby) {
	}

	@Override
	public void saveState(IMemento memento) {
	}

	@Override
	public void addPropertyListener(IPropertyListener listener) {
	}

	static Image cefImage;
	
	public static final String EXTENSION_POINT_INTRO_ADVERTISEMENT_MESSAGE = "advertisementMessage";
	public static final String EXTENSION_POINT_INTRO_ADVERTISEMENT_PERSPECTIVE = "perspectiveID";
	
	@Override
	public void createPartControl(Composite container) {
    	final Composite outerContainer = new Composite(container, SWT.NONE);

    	GridData gridData = new GridData();
    	gridData.horizontalAlignment = GridData.FILL;
    	gridData.verticalAlignment = GridData.FILL;
    	gridData.grabExcessHorizontalSpace = true;
    	gridData.grabExcessVerticalSpace = true;
    	outerContainer.setLayoutData(gridData);
    	
    	outerContainer.addListener(SWT.Resize, new Listener() {
    		public void handleEvent(Event event) {
    			Rectangle rect = outerContainer.getClientArea();
    			ImageDescriptor backgroundImageDesc = BrandingPlugin.imageDescriptorFromPlugin(BrandingPlugin.PLUGIN_ID, "resources/images/WelcomeScreen/CEF_grey.jpg");
    			Image backgroundImage = backgroundImageDesc.createImage();
    			GC gc = new GC(backgroundImage);
    			gc.setAntialias(SWT.ON);
    			gc.setInterpolation(SWT.HIGH);
    			gc.drawImage(backgroundImage, 0, 0, backgroundImage.getBounds().width, backgroundImage.getBounds().height, 0, 0, rect.width, rect.height);
    			gc.dispose();
    			outerContainer.setBackgroundImage(backgroundImage);
    			if (cefImage != null) {
					cefImage.dispose();
				}
    			cefImage = backgroundImage;
    		}
    	});

    	ImageDescriptor loginImageDesc = BrandingPlugin.imageDescriptorFromPlugin(BrandingPlugin.PLUGIN_ID, "resources/images/WelcomeScreen/arrow-48.png");
		Image loginImage = loginImageDesc.createImage();
    	
    	GridLayout gridLayout = new GridLayout();
    	gridLayout.numColumns = 2;
    	
    	outerContainer.setLayout(gridLayout);
    	
    	//adding some style text for a welcome header	
    	StyledText welcomeText = new StyledText(outerContainer, SWT.NULL);
    	welcomeText.setText("Welcome to the Virtual Satellite CEF");
    	
    	final int FONT_SIZE_32 = 32;
    	final int FONT_SIZE_18 = 18;
    	
    	Font boldFont = new Font(welcomeText.getDisplay(), new FontData("Arial", FONT_SIZE_32, SWT.BOLD));
    	welcomeText.setForeground(outerContainer.getDisplay().getSystemColor(SWT.COLOR_BLACK));
    	welcomeText.setFont(boldFont);
    	
    	gridData = new GridData();
    	gridData.grabExcessHorizontalSpace = true;
    	gridData.horizontalSpan = 2; 
    	gridData.horizontalAlignment = GridData.FILL;
    	gridData.verticalAlignment = GridData.FILL;
    	welcomeText.setLayoutData(gridData);
    	
    	IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(PluginXml.ExtensionPoints.Dedlrscvirsatcefintrocontribution.ID);

    	for (IConfigurationElement cElement : configElements) {
     	
    		String perspectiveId = cElement.getAttribute(EXTENSION_POINT_INTRO_ADVERTISEMENT_PERSPECTIVE);
    		String message = cElement.getAttribute(EXTENSION_POINT_INTRO_ADVERTISEMENT_MESSAGE);
    		
	      	// adding an icon button for start modeling
	    	CLabel startLabel = new CLabel(outerContainer, SWT.NULL);
	    	startLabel.setImage(loginImage);
	    	startLabel.setSize(loginImage.getBounds().width, loginImage.getBounds().height);
	    	
	    	gridData = new GridData();
	     	gridData.horizontalAlignment = GridData.FILL;
	    	gridData.verticalAlignment = GridData.FILL;
	    	gridData.grabExcessHorizontalSpace = false;
	    	startLabel.setLayoutData(gridData);	
	    	
	    	startLabel.addMouseListener(new OpenPerspectiveMouseListener(perspectiveId));
	    	
	    	// adding a link for start modeling
	    	StyledText startLink = new StyledText(outerContainer, SWT.NULL);
	    	Font notSoBoldFont = new Font(welcomeText.getDisplay(), new FontData("Arial", FONT_SIZE_18, SWT.UNDERLINE_LINK));
	    	startLink.setFont(notSoBoldFont);
	    	startLink.setForeground(outerContainer.getDisplay().getSystemColor(SWT.COLOR_BLACK));
	    	startLink.setText(message);
	    	
	    	gridData = new GridData(); 
	    	gridData.horizontalAlignment = GridData.FILL;
	    	gridData.verticalAlignment = GridData.CENTER;
	    	startLink.setLayoutData(gridData);
	
	    	startLink.addMouseListener(new OpenPerspectiveMouseListener(perspectiveId));
    	}
	}

	@Override
	public void dispose() {
		cefImage.dispose();
	}

	@Override
	public Image getTitleImage() {
		ImageDescriptor titleDesc = BrandingPlugin.imageDescriptorFromPlugin(BrandingPlugin.PLUGIN_ID, "resources/images/Branding/CEF16.gif");
		Image titleImage = titleDesc.createImage();
		return titleImage;
	}

	@Override
	public String getTitle() {
		return "Welcome to Virtual Satellite CEF";
	}

	@Override
	public void removePropertyListener(IPropertyListener listener) {
	}

	@Override
	public void setFocus() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	/**
	 * Little embedded class to define a mouse listener on the text label in the Welcome Screen
	 * @author scha_vo
	 *
	 */
	class OpenPerspectiveMouseListener implements MouseListener {

		private String perspectiveId;
		
		/**
		 * Constructs a mouse listener which will open the given perspective
		 * @param perspectiveId the id of the perspective to be opened by the listener
		 */
		OpenPerspectiveMouseListener(String perspectiveId) {
			this.perspectiveId = perspectiveId;
		}
		
		@Override
		public void mouseDoubleClick(MouseEvent e) {
			mouseDown(e);
		}

		@Override
		public void mouseDown(MouseEvent e) {
			try {
				IIntroPart welcomePart = PlatformUI.getWorkbench().getIntroManager().getIntro();
				PlatformUI.getWorkbench().getIntroManager().closeIntro(welcomePart);
				PlatformUI.getWorkbench().showPerspective(perspectiveId, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			} catch (Exception ex) {
				BrandingPlugin.getDefault().getLog().log(new Status(Status.ERROR, "BrandingPlugin", "Login command not found or perspective not found"));
			}
		}

		@Override
		public void mouseUp(MouseEvent e) {
		}
	}
}