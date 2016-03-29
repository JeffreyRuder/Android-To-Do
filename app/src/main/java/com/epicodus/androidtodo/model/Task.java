package com.epicodus.androidtodo.model;


public class Task {
    private String mTitle;
    private String mDescription;

    public Task(String title, String description) {
        mTitle= title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String newTitle) {
        mTitle = newTitle;
    }

    public void setDescription(String newDescription) {
        mDescription = newDescription;
    }
}
