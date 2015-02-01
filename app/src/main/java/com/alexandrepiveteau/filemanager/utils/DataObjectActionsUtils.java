package com.alexandrepiveteau.filemanager.utils;

import android.content.Context;
import android.widget.Toast;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.files.DataObject;

import java.util.List;

/**
 * Created by Alexandre on 2/1/2015.
 */
public class DataObjectActionsUtils {
    public static void renameDataObject(Context context, List<DataObject> dataObjects) {
        if(dataObjects.size() != 1) {
            Toast.makeText(context, R.string.rename_too_many_items, Toast.LENGTH_LONG).show();
        }
    }
}
