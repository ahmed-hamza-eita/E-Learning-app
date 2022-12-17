package com.hamza.e_learningapp.utils;

public class Helper {
    public static String putDot(String email) {
        return email.replace("*", ".");
    }

    public static String removeDot(String email) {
        return email.replace(".", "*");
    }
}
