package de.dlr.sc.virsat.cef.loadtest.model;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class DragAndDroppedItem {
	private SWTBotTreeItem item;
    private int numberOfTimesToBeDragAndDropped;
    private SWTBotTreeItem parentItem;
    
    public DragAndDroppedItem(SWTBotTreeItem item, int numberOfTimesToBeDragAndDropped, SWTBotTreeItem parentItem) {
        this.item = item;
        this.numberOfTimesToBeDragAndDropped = numberOfTimesToBeDragAndDropped;
        this.parentItem = parentItem;
    }

    public SWTBotTreeItem getItem() {
        return item;
    }

    public int getNumberOfTimesToBeDragAndDropped() {
        return numberOfTimesToBeDragAndDropped;
    }
    
    public SWTBotTreeItem getParentItem() {
    	return parentItem;
    }
}
