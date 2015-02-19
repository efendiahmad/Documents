package com.alexandrepiveteau.filemanager.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alexandrepiveteau.filemanager.files.DataObject;
import com.alexandrepiveteau.filemanager.ui.listeners.OnDataActionPerformedListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by Alexandre on 2/6/2015.
 */
// TODO : Handle exceptions correctly
// TODO : Use Threads

public class DataObjectActionsCopyUtils {

    public static void copyDataObjects(final Activity context, final List<DataObject> dataObjects, final List<File> dest ,final OnDataActionPerformedListener listener) {
        for(final DataObject dataObject : dataObjects) {
            new Thread() {
                 @Override
                 public void run() {
                    super.run();
                    try {
                        copyFolder(dataObject.getFile(), dest.get(dataObjects.indexOf(dataObject)));
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDataActionPerformed();
                            }
                        });
                    }
                    catch (IOException e) {
                        Log.e("FileManager", "Error while copying files");
                    }
                }
            }.start();
        }
    }

    public static void copyFolder(File src, File dest)
            throws IOException{

        if(src.isDirectory()){

            if(!dest.exists()){
                dest.mkdir();
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }

        }else{
            FileChannel sourceChannel = null;
            FileChannel destChannel = null;

            try {
                sourceChannel = new FileInputStream(src).getChannel();
                destChannel = new FileOutputStream(dest).getChannel();
                destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            }finally{
                //if(sourceChannel != null)
                sourceChannel.close();
                //if(destChannel != null)
                destChannel.close();
            }
        }
    }
}
