package io.th0rgal.andrew.backend.features;

public class FeaturesManager {

    private String input;

    public FeaturesManager(String input) {
        this.input = input;

    }

    public String delegate() {

        switch (input) {
            case "timetable":
                return new Timetable().getCurrentActivity();
            default:
                return null;
        }

    }


}
