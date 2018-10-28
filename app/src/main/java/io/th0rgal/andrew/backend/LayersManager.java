package io.th0rgal.andrew.backend;

import io.th0rgal.andrew.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LayersManager {

    private static File layerAFile = new File(Utils.dataDirectory, "layerA.yml");

    private static File layerBFile = new File(Utils.dataDirectory, "layerB.json");
    private static List<String> layerB;

    private static File layerCFile = new File(Utils.dataDirectory, "layerC.yml");

    public static File getLayerA() {
        if (!layerAFile.exists())
            try {
                FileUtils.copyURLToFile(LayersManager.class.getResource("/layerA.yml"), layerAFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return layerAFile;
    }

    public static List<String> getLayerB() {

        if (layerB != null)
            return layerB;

        layerB = new ArrayList<>();
        if (!layerBFile.exists())
            try {
                copyInputStreamToFile(Utils.assetManager.open("layerB.json"), layerBFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            JSONArray jsonarray = new JSONArray(IOUtils.toString(new FileReader(layerBFile)));
            for (int i = 0; i < jsonarray.length(); i++)
                layerB.add(jsonarray.getString(i));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return layerB;
    }

    public static File getLayerC() {
        if (!layerCFile.exists())
            try {
                FileUtils.copyURLToFile(LayersManager.class.getResource("/layerC.yml"), layerCFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return layerCFile;
    }

    private static void copyInputStreamToFile(InputStream in, File file) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if (out != null) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
