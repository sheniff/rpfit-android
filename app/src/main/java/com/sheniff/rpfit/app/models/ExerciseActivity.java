package com.sheniff.rpfit.app.models;

public class ExerciseActivity {
    private int[] reps;
    private int series;
    private int restSeconds;

    private Exercise exercise;
    
    public ExerciseActivity() {
    }

    public int[] getReps() {
        return reps;
    }

    public void setReps(int[] reps) {
        this.reps = reps;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(int restSeconds) {
        this.restSeconds = restSeconds;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
