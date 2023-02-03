package com.hamza.e_learningapp.utils;

import java.util.Random;

public class Helper {
    public static String putDot(String email) {
        return email.replace("*", ".");
    }

    public static String removeDot(String email) {
        return email.replace(".", "*");
    }

    public static int generateCode() {
        Random random = new Random();
        int num = 9999 - 1000;
        int code = random.nextInt(num) + 1000;
        return code;
    }
}
