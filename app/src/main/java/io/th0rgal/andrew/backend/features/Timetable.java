package io.th0rgal.andrew.backend.features;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class Timetable {

    public String getCurrentActivity() {

        return getActivityAt(new LocalDate(), new LocalTime());
    }

    public String getActivityAt(LocalDate date, LocalTime time) {

        try {
            int day = date.getDayOfWeek() - 1;
            int hour = time.getHourOfDay();
            if (time.getMinuteOfHour() > 45)
                hour++;

            int activityID = day * 10 - 1 + hour - 7;

            if (hour - 7 > 10)
                return "Tu n'as pas cours espèce de boloss !";

            Cours cours;
            int weekType;
            if (activities[activityID].length > 1)
                weekType = date.getWeekOfWeekyear() % 2 - 1;
            else
                weekType = 0;

            cours = activities[activityID][weekType];

            if (cours == Cours.PERM) {
                int iterator = activityID + 1;
                Cours nextCours = activities[iterator][weekType];
                while (nextCours != null && nextCours == Cours.PERM) {
                    iterator++;
                    nextCours = activities[iterator][weekType];
                }
                if (nextCours == null) {
                    return "Eh bien je crois que tu es en weekend !";
                } else {
                    return "Tu n'as pas cours pour l'instant, tu reprendras avec " + nextCours.toString() + " dans " + (iterator - activityID) + "heure(s) !";
                }
            } else {
                return "Tu as " + cours;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Je suis désolé mais je n'arrive pas à lire";
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
