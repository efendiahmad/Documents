package com.alexandrepiveteau.filemanager.files;

import android.content.Context;

import com.alexandrepiveteau.filemanager.comparators.DataObjectAlphabeticalComparator;
import com.alexandrepiveteau.filemanager.utils.ExtensionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandre on 1/24/2015.
 */
public class DataObject {

    public static enum ACTION {COPY, CUT, RENAME, DELETE, NONE};

    private int backgroundIcon;
    private String description;
    private File file;
    private int icon;
    private String name;

    public static List<DataObject> getFilesFrom(Context context, File path, String query) {
        List<DataObject> fileList = new ArrayList<DataObject>();
        for (File f : path.listFiles()) {
            if(f.isHidden()) {

            } else if(f.isFile()) {
                String fileName = f.getName();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(f.lastModified());
                DataObject obj = new DataObject();
                obj.setBackgroundIcon(ExtensionUtils.getExtensionBackground(f));
                obj.setIcon(ExtensionUtils.getExtensionImage(f));
                obj.setFile(f);
                obj.setName(fileName);
                obj.setDescription(c.getTime().toLocaleString());
                if(obj.getName().contains(query))
                    fileList.add(obj);
            }
        }
        Collections.sort(fileList, new DataObjectAlphabeticalComparator());
        return fileList;
    }

    public static List<DataObject> getDirectoriesFrom(Context context, File path, String query) {
        List<DataObject> directoryList = new ArrayList<DataObject>();
        for (File f : path.listFiles()) {
            if(f.isHidden()) {

            }else if (f.isDirectory()) {
                String fileName = f.getName();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(f.lastModified());
                DataObject obj = new DataObject();
                obj.setBackgroundIcon(ExtensionUtils.getExtensionBackground(f));
                obj.setIcon(ExtensionUtils.getExtensionImage(f));
                obj.setFile(f);
                obj.setName(fileName);
                obj.setDescription(c.getTime().toLocaleString());
                if(obj.getName().contains(query))
                    directoryList.add(obj);
            }
        }
        Collections.sort(directoryList, new DataObjectAlphabeticalComparator());
        return directoryList;
    }

    public DataObject() {}

    public int getBackgroundIcon() {
        return backgroundIcon;
    }

    public String getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public void setBackgroundIcon(int backgroundIcon) {
        this.backgroundIcon = backgroundIcon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFile (File file) {
        this.file = file;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }
}
