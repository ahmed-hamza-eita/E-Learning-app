package com.hamza.e_learningapp.data;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefrance {

    private static Context appContext ;
    private static final String SHARED_PREFERENCES_NAME = "user data";
    private static final String USER_NAME_KEY = "user name" ;
    private static final String USER_EMAIL_KEY = "user email" ;
    private static final String USER_PHOTO_KEY = "user photo" ;
    private static final String USER_ID_KEY = "user id" ;
    private static final String USER_TYPE = "user type" ;




    private MySharedPrefrance() {
    }
    //methods

    public static void init(Context context) {
       appContext = context;
    }

    private static SharedPreferences getSharedPreferences() {
        return appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public static void setUserName(String userName) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_NAME_KEY, userName).apply();
    }
    public static String getUserName() {
        return getSharedPreferences().getString(USER_NAME_KEY, "");
    }

    public static void setUserEmail(String userEmail) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_EMAIL_KEY, userEmail).apply();
    }
    public static String getUserEmail() {
        return getSharedPreferences().getString(USER_EMAIL_KEY, "");
    }

    public static void setUserPhoto(String userEmail) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_PHOTO_KEY, userEmail).apply();
    }
    public static String getUserPhoto() {
        return getSharedPreferences().getString(USER_PHOTO_KEY, "");
    }


    public static void setUserId(String userEmail) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_ID_KEY, userEmail).apply();
    }
    public static String getUserId() {
        return getSharedPreferences().getString(USER_ID_KEY, "");
    }

    public static void setUserType(String userEmail) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_TYPE, userEmail).apply();
    }
    public static String getUserType() {
        return getSharedPreferences().getString(USER_TYPE, "");
    }



    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }


    public static void clear() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }
}
