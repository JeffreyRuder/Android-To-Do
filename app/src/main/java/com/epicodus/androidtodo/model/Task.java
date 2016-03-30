package com.epicodus.androidtodo.model;


import java.util.Objects;

public class Task {
    String title;
    String description;

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof Task)) {
            return false;
        } else {
            Task otherTask = (Task) otherObject;
            return this.getTitle().equals(otherTask.getTitle()) &&
                    this.getDescription().equals(otherTask.getDescription());
        }
    }
}
