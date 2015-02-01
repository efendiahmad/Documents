package com.alexandrepiveteau.filemanager.files;

/**
 * Created by Alexandre on 1/30/2015.
 */
public class DataObjectAction {

    private DataObject.ACTION mAction;
    private int mActionIcon;
    private String mActionText;

    public DataObject.ACTION getAction() {
        return mAction;
    }

    public int getActionIcon() {
        return mActionIcon;
    }

    public String getActionText() {
        return mActionText;
    }

    public void setAction(DataObject.ACTION action) {
        mAction = action;
    }

    public void setActionIcon(int actionIcon) {
        mActionIcon = actionIcon;
    }

    public void setActionText(String actionText) {
        mActionText = actionText;
    }
}
