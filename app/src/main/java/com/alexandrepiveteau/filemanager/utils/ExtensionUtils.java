package com.alexandrepiveteau.filemanager.utils;

import com.alexandrepiveteau.filemanager.R;

import java.io.File;

/**
 * Created by Alexandre on 1/26/2015.
 */
public class ExtensionUtils {

    private static String [] APK_EXTENSIONS = {".apk"};
    private static String [] COMPRESSED_EXTENSIONS = {".7z", ".zip"};
    private static String [] MUSIC_EXTENSIONS = {".3gp", ".act", ".aiff", ".aac", ".amr", ".au",
            ".awb", ".dct", ".dss", ".dvf", ".flac", ".gsm", ".iklax", ".ivs", ".m4a", ".mmf",
            ".mp3", ".mpc", ".msv", ".oga", ".ogg", ".opus", ".ra", ".rm", ".sln", ".tta", ".vox",
            ".wav", ".wma", ".wv"};
    private static String [] MOVIES_EXTENSIONS = {".3g2", ".3gp", ".mp4"};
    private static String [] PDF_EXTENSIONS = {".pdf"};
    private static String [] PHOTOS_EXTENSIONS = {".jpeg", ".jpg", ".png"};

    public static int getExtensionBackground(File file) {
        if(file.isDirectory()) {
            return R.drawable.file_icon_circle_grey;
        } else if (containsExtension(file.getPath(), APK_EXTENSIONS)) {
            return R.drawable.file_icon_circle_green;
        } else if (containsExtension(file.getPath(), COMPRESSED_EXTENSIONS)) {
            return R.drawable.file_icon_circle_grey;
        } else if (containsExtension(file.getPath(), MUSIC_EXTENSIONS)) {
            return R.drawable.file_icon_circle_orange;
        } else if (containsExtension(file.getPath(), MOVIES_EXTENSIONS)) {
            return R.drawable.file_icon_circle_red;
        } else if (containsExtension(file.getPath(), PDF_EXTENSIONS)) {
            return R.drawable.file_icon_circle_red;
        } else if (containsExtension(file.getPath(), PHOTOS_EXTENSIONS)) {
            return R.drawable.file_icon_circle_blue;
        } else {
            return R.drawable.file_icon_circle_yellow;
        }
    }

    public static int getExtensionImage(File file) {
        if(file.isDirectory()) {
            return R.drawable.ic_file_folder;
        } else if (containsExtension(file.getPath(), APK_EXTENSIONS)) {
            return R.drawable.ic_file_apk;
        } else if (containsExtension(file.getPath(), COMPRESSED_EXTENSIONS)) {
            return R.drawable.ic_file_compressed;
        } else if (containsExtension(file.getPath(), MUSIC_EXTENSIONS)) {
            return R.drawable.ic_file_music;
        } else if (containsExtension(file.getPath(), MOVIES_EXTENSIONS)) {
            return R.drawable.ic_file_movie;
        } else if (containsExtension(file.getPath(), PHOTOS_EXTENSIONS)) {
            return R.drawable.ic_file_photo;
        } else {
            return R.drawable.ic_file_default;
        }
    }

    private static boolean containsExtension(String path, String [] extensions) {
        for(String extension : extensions) {
            if(path.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
