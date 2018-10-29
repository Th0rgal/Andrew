package io.th0rgal.andrew.backend;

import io.th0rgal.andrew.utils.ListUtils;
import io.th0rgal.andrew.utils.Utils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LayersManager {


    private static File layerAFile = new File(Utils.getDataDirectory(), "layerA.json");
    private static JSONObject layerA;

    private static File layerBFile = new File(Utils.getDataDirectory(), "layerB.json");
    private static List<String> layerB;

    private static File layerCFile = new File(Utils.getDataDirectory(), "layerC.json");


    public static JSONObject getLayerA() {

        if (layerA == null) {
            if (!layerAFile.exists())
                try {
                    copyInputStreamToFile(Utils.getAssetManager().open("layerA.json"), layerAFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            try {
                layerA = new JSONObject(IOUtils.toString(new FileReader(layerAFile)));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return layerA;
    }

    public static List<String> getLayerB() {

        if (layerB == null) {

            layerB = new ArrayList<>();
            if (!layerBFile.exists())
                try {
                    copyInputStreamToFile(Utils.getAssetManager().open("layerB.json"), layerBFile);
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
        }
        return layerB;
    }

    public static File getLayerC() {
        if (!layerCFile.exists())
            try {
                layerCFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return layerCFile;
    }

    public static String submitToLayerA(String input) throws JSONException {
        if (layerA == null)
            layerA = getLayerA();

        //We iterate the input from layerB
        for (Iterator<String> it = layerA.getJSONObject("input").keys(); it.hasNext(); ) {
            String sectionName = it.next();
            JSONArray section = layerA.getJSONObject("input").getJSONArray(sectionName);
            List<String> sentences = new ArrayList<>();
            for (int sentenceIndex = 0; sentenceIndex < section.length(); sentenceIndex++)
                sentences.add(section.getString(sentenceIndex));

            if (Analyzer.searchForSimilar(input, sentences) != null) {
                //we get the output section corresponding to the input one
                JSONArray outputArray = layerA.getJSONObject("output").getJSONArray(sectionName);
                List<String> outputList = new ArrayList<>();
                for (int outputArrayIndex = 0; outputArrayIndex < outputArray.length(); outputArrayIndex++)
                    outputList.add(outputArray.getString(outputArrayIndex));
                return ListUtils.random(outputList);
            }
        }
        return null;
    }

    public static String submitToLayerB(String input) {
        return Analyzer.searchForSimilar(input, LayersManager.getLayerB());
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
