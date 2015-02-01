package com.alexandrepiveteau.filemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.alexandrepiveteau.filemanager.R;

import java.io.File;

/**
 * Created by Alexandre on 1/30/2015.
 */
public class DataObjectOpenerUtils {
    public static File openFile(File oldDir, File dir, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String mimeType;
        try {
            mimeType = myMime.getMimeTypeFromExtension(MimeUtils.fileExt(dir.toString()).toLowerCase().substring(1));
            intent.setDataAndType(Uri.fromFile(dir),mimeType);
        } catch (Exception e) {
            Toast.makeText(context, R.string.access_denied, Toast.LENGTH_LONG).show();
            dir = oldDir;
            return dir;
        }
        //Uncomment this if we want to open the file in a new Task in Overview (will cause weird back button behavior)
        //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, R.string.install_compatible_app, Toast.LENGTH_LONG).show();
        }
        dir = oldDir;
        return dir;
    }
}
