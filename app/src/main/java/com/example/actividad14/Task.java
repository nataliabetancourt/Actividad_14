package com.example.actividad14;

public class Task {
    private String name, description, id, state, time, userKey;

    public Task() {
        this.name = "";
        this.description = "";
        this.state = "";
        this.time = "";
        this.id = "";
        this.userKey = "";
    }

    public Task(String name, String description, String id, String state, String time, String userKey) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.state = state;
        this.time = time;
        this.userKey = userKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
