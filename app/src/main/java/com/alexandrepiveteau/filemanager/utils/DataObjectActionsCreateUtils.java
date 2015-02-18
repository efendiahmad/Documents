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
 * Created by Alexandre on 18.02.2015.
 */
public class DataObjectActionsCreateUtils {

    public static void createDataObject(final boolean isDirectory, final Context context, final File dir, final OnDataActionPerformedListener listener) {

        // TODO : Rework this part of code to have a custom Dialog class
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(isDirectory) {
            builder.setTitle(R.string.action_create_folder);
        }
        else {
            builder.setTitle(R.string.action_create_file);
        }
        final EditText input = new EditText(context);
        builder.setView(input);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mText = input.getText().toString();
                File file = new File(dir, mText);
                //if(file.isFile()) {
                if(isDirectory) {
                    file.mkdirs();
                }
                else{
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.access_denied, Toast.LENGTH_LONG).show();
                    }
                }
                //}
                //else /*if (file.isDirectory())*/ {
                //    Toast.makeText(context, R.string.access_denied, Toast.LENGTH_LONG).show();
                //}
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
