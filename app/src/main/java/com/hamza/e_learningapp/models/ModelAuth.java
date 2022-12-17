package com.hamza.e_learningapp.models;

public class ModelAuth {
    private String name,email,type,userID;

    public ModelAuth() {
    }

    public ModelAuth(String name, String email, String type) {
        this.name = name;
        this.email = email;
        this.type = type;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
