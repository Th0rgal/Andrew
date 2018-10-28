package io.th0rgal.andrew.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

    public static <T> int[] indexOfMultiple(List<T> list, T object) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(object)) {
                indices.add(i);
            }
        }
        // ArrayList<Integer> to int[] conversion
        int[] result = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            result[i] = indices.get(i);
        }
        return result;
    }

    public static <T> T random(List<T> list) {
        int index = new Random().nextInt(list.size());
        return list.get(index);
    }
}
