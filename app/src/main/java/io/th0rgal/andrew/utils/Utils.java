package io.th0rgal.andrew.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;

public class Utils {

    private static String dataDirectory;

    private static AssetManager assetManager;

    private static Resources resoures;

    public static Resources getResources() {
        return Utils.resoures;
    }

    public static void setResources(Resources resources) {
        Utils.resoures = resources;
    }

    public static String getDataDirectory() {
        return Utils.dataDirectory;
    }

    public static void setDataDirectory(String dataDirectory) {
        Utils.dataDirectory = dataDirectory;
    }

    public static AssetManager getAssetManager() {
        return Utils.assetManager;
    }

    public static void setAssetManager(AssetManager assetManager) {
        Utils.assetManager = assetManager;
    }

}
