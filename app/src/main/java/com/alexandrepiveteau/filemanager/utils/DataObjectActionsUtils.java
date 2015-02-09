package com.alexandrepiveteau.filemanager.utils;

import android.content.Context;

import com.alexandrepiveteau.filemanager.files.DataObject;
import com.alexandrepiveteau.filemanager.ui.listeners.OnDataActionPerformedListener;

import java.io.File;
import java.util.List;

/**
 * Created by Alexandre on 2/1/2015.
 */

public class DataObjectActionsUtils {

    public static void copyDataObjects(Context context, final List<DataObject> dataObjects, final List<File> dest ,final OnDataActionPerformedListener listener) {
        DataObjectActionsCopyUtils.copyDataObjects(context, dataObjects, dest, listener);
    }

    public static void cutDataObjects(Context context, final List<DataObject> dataObjects, final List<File> dest ,final OnDataActionPerformedListener listener) {
        DataObjectActionsCopyUtils.copyDataObjects(context, dataObjects, dest, listener);
        deleteDataObjects(dataObjects, listener);
    }

    public static void deleteDataObjects(final List<DataObject> dataObjects,final OnDataActionPerformedListener listener) {
        for(DataObject object : dataObjects) {
            object.getFile().setWritable(true);
            if(object.getFile().isDirectory()) {
                deleteDirectory(object.getFile());
            } else {
                object.getFile().delete();
            }
        }
        listener.onDataActionPerformed();
    }

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public static void renameDataObject(Context context, final List<DataObject> dataObjects, final OnDataActionPerformedListener listener) {
        DataObjectActionsRenameUtils.renameDataObject(context, dataObjects, listener);
    }
}
