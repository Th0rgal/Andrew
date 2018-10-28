package io.th0rgal.andrew.utils;

import android.content.res.AssetManager;

public class Utils {

    private static String dataDirectory;

    private static AssetManager assetManager;

    public static String getDataDirectory(){
        return Utils.dataDirectory;
    }

    public static void setDataDirectory(String dataDirectory){
        Utils.dataDirectory = dataDirectory;
    }

    public static AssetManager getAssetManager(){
        return Utils.assetManager;
    }

    public static void setAssetManager(AssetManager assetManager){
        Utils.assetManager = assetManager;
    }

}
