package com.alexandrepiveteau.filemanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.files.DataObject;
import com.alexandrepiveteau.filemanager.ui.listeners.OnDataActionPerformedListener;

import java.io.File;
import java.util.List;

/**
 * Created by Alexandre on 2/6/2015.
 */
public class DataObjectActionsRenameUtils {

    public static void renameDataObject(Context context, final List<DataObject> dataObjects, final OnDataActionPerformedListener listener) {

        //If the size of the stuff to rename isn't right, we inform the user and quit.
        if(dataObjects.size() != 1) {
            Toast.makeText(context, R.string.rename_too_many_items, Toast.LENGTH_LONG).show();
            return;
        }

        // TODO : Rework this part of code to have a custom Dialog class
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.rename);
        final EditText input = new EditText(context);
        builder.setView(input);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mText = input.getText().toString();
                File file = new File(dataObjects.get(0).getFile().getPath());
                file.renameTo(new File(file.getParentFile(), mText));
                listener.onDataActionPerformed();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                listener.onDataActionCancelled();
            }
        });

        builder.show();
    }
}
