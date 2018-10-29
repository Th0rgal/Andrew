package io.th0rgal.andrew.backend;


import io.th0rgal.andrew.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;


public class Analyzer {


    public static String searchForSimilar(String message, List<String> messagesList) {

        List<String> possibleAnswers = new ArrayList<>();

        for (int i = 0; i < messagesList.size(); i++)
            for (String line : messagesList.get(i).split("\n"))
                if (areSimilar(message.toLowerCase(), line.toLowerCase()))
                    possibleAnswers.add(messagesList.get(i + 1));

        return (possibleAnswers.size() == 0)
                // LA LISTE EST VIDE ? => ON PREND AU PIF DANS LA DATABASE
                ? null
                // LA LISTE EST PLEINE ? => ON PREND AU PIF DEDANS
                : ListUtils.random(possibleAnswers);
    }

    public static boolean areSimilar(String s0, String s1) {
        int somme = s0.length() + s1.length();
        return (getLevenshteinDistance(s0, s1) <
                // dsl pour le fr mais j'ai pas le lvl pour l'écrire ...et le comprendre en anglais, déjà que je galère
                // LA DISTANCE DE LEVENSHTEIN MAXIMUM AUTORISEE POUR QUE LES STRINGS SOIENT SIMILAIRES EST EGALE A :
                // la moyenne de  [ la racine carrée de la somme des longueurs * 1/30 de la somme des longueurs]
                (Math.sqrt(somme) + 1D / 30D * somme) * 2D / 3D - 0.5D);
    }

    public static int getLevenshteinDistance(String s0, String s1) {
        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamicaly computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {

            // initial cost of skipping prefix in String s1
            newcost[0] = j - 1;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {

                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }


}
