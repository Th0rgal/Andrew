package io.th0rgal.andrew.backend.features;

import java.util.Calendar;

public class Timetable {

    public String getCurrentActivity() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return getActivityAt(calendar);
    }

    public String getActivityAt(Calendar calendar) {
        try {
            int day = (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
            System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
            int activityID = day * 10 + calendar.get(Calendar.HOUR_OF_DAY) - 8;
            if (activityID >= activities.length) {
                int semaine = calendar.get(Calendar.WEEK_OF_YEAR) % 2;
                if (activities[activityID].length > 1)
                    return activities[activityID][calendar.get(Calendar.WEEK_OF_YEAR) % 2].toString();
                return activities[activityID][0].toString();
            }
            return Cours.PERM.toString();
        } catch (Exception e) {
            return Cours.PERM.toString();
        }
    }


    public enum Cours {
        MATHS("cours de Mathématiques"),
        SVT("cours de SVT"),
        TPE("TPE"),
        ANGLAIS("cours d'Anglais"),
        EMC("cours d'EMC"),
        ISN("cours d'ISN"),
        ESPAGNOL("cours d'Espagnol"),
        HISTGEO("cours d'Histoire Géo"),
        AP("AP"),
        PHYSIQUE("cours de Physique Chimie"),
        FRANCAIS("cours de français"),
        EURO("cours d'euro"),
        SPORT("sport"),
        PERM("pas cours"),
        BIAIS30M("encore 30 minutes du cours précédent");

        private String name;

        //Constructeur
        Cours(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

    }

    Cours[][] activities = {
            {Cours.MATHS}, {Cours.SVT}, {Cours.TPE}, {Cours.TPE}, {Cours.PERM}, {Cours.ANGLAIS}, {Cours.EMC}, {Cours.MATHS}, {Cours.ISN}, {Cours.BIAIS30M},
            {Cours.SVT}, {Cours.SVT}, {Cours.ESPAGNOL}, {Cours.ANGLAIS, Cours.HISTGEO}, {Cours.PERM}, {Cours.AP}, {Cours.AP}, {Cours.PHYSIQUE}, {Cours.PHYSIQUE}, {Cours.PERM},
            {Cours.MATHS}, {Cours.FRANCAIS}, {Cours.FRANCAIS}, {Cours.EURO}, {Cours.PERM}, {Cours.PERM}, {Cours.PERM}, {Cours.PERM}, {Cours.PERM}, {Cours.PERM},
            {Cours.PERM}, {Cours.MATHS}, {Cours.HISTGEO}, {Cours.HISTGEO}, {Cours.PERM}, {Cours.PERM}, {Cours.FRANCAIS}, {Cours.PERM, Cours.ESPAGNOL}, {Cours.EURO}, {Cours.PERM},
            {Cours.SPORT}, {Cours.SPORT}, {Cours.FRANCAIS}, {Cours.ESPAGNOL}, {Cours.PERM}, {Cours.ANGLAIS}, {Cours.PHYSIQUE}, {Cours.PHYSIQUE}, {Cours.PERM}, {Cours.PERM}
    };

}
