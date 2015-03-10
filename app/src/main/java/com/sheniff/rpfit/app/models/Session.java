package com.sheniff.rpfit.app.models;

import java.util.ArrayList;

public class Session {
    private String name;
    private ArrayList<Integer> daysOfWeek;
    private ArrayList<ExerciseActivity> exercises;

    public Session() {
        this("");
    }

    public Session(String name) {
        this.name = name;
        this.daysOfWeek = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(ArrayList<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public ArrayList<ExerciseActivity> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExerciseActivity> exercises) {
        this.exercises = exercises;
    }
}
