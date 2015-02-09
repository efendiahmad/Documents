package com.alexandrepiveteau.filemanager.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Alexandre on 2/9/2015.
 */
public class FilesUtils {
    //First file: we make all the other files references from this one
    public static final File INTERNAL_STORAGE = Environment.getExternalStorageDirectory();

    //The other "special" files listed
    public static final File DCIM = new File(INTERNAL_STORAGE, "DCIM");
    public static final File MUSIC = new File(INTERNAL_STORAGE, "Music");
    public static final File DOWNLOADS = new File(INTERNAL_STORAGE, "Download");
}
