package com.epicodus.androidtodo.model;


public class Task {
    String title;
    String description;
    String ref;

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

    public void setRef(String newRef) {
        ref = newRef;
    }

    public String getRef() {
        return ref;
    }
}
