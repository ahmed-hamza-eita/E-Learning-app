package com.hamza.e_learningapp.models;

public class ModelChat {
    String message, senderId, userName;

    public ModelChat() {
    }

    public ModelChat(String message, String senderId, String userName) {
        this.message = message;
        this.senderId = senderId;
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getUserName() {
        return userName;
    }
}
