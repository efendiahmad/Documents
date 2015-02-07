package com.alexandrepiveteau.filemanager.utils;

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

    public static void copyDataObjects(Context context, final List<DataObject> dataObjects, final List<File> dest ,final OnDataActionPerformedListener listener) {
        int i = 0;
        for(DataObject dataObject : dataObjects) {
            try {
                copyFolder(dataObject.getFile(), dest.get(i));
            }
            catch (IOException e) {
                Log.e("FileManager", "Error while copying files");
                break;
            }
            i++;
        }
        listener.onDataActionPerformed();
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
