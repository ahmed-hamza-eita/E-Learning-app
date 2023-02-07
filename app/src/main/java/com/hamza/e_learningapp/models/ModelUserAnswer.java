package com.hamza.e_learningapp.models;

public class ModelUserAnswer {
    private String userName ;
    private String email ;
    private String userId ;
    private double grade ;

    public ModelUserAnswer(String userName, String email, String userId, int grade) {
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.grade = grade ;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
