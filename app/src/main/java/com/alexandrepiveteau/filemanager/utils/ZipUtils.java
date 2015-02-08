package com.alexandrepiveteau.filemanager.utils;

import android.content.Context;
import android.widget.Toast;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.files.DataObject;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * Created by Alexandre on 2/8/2015.
 */
public class ZipUtils {

    public static boolean isZipDataObject(DataObject dataObject) {
        return dataObject.getFile().getPath().toLowerCase().endsWith(".zip");
    }

    public static void unzipDataObject(DataObject dataObject, Context context) {
        String password = "password";

        try {
            ZipFile zipFile = new ZipFile(dataObject.getFile().getPath());
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
                throw new ZipException("The zip is encrypted. Could not decrypt it.");
            }
            zipFile.extractAll(replaceLast(dataObject.getFile().getPath(), ".zip", ""));
            Toast.makeText(context, context.getText(R.string.file_unzipped_at) + " " + replaceLast(dataObject.getFile().getPath(), ".zip", "") + ".", Toast.LENGTH_LONG).show();
        }
        catch (ZipException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Convenience method
    private static String replaceLast(String string, String from, String to) {
        int lastIndex = string.lastIndexOf(from);
        if (lastIndex < 0) return string;
        String tail = string.substring(lastIndex).replaceFirst(from, to);
        return string.substring(0, lastIndex) + tail;
    }
}
