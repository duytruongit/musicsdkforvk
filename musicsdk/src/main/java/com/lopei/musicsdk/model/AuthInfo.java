package com.lopei.musicsdk.model;

/**
 * Created by alan on 15.12.16.
 */

public class AuthInfo {
    private static String accessToken;
    private static String userId;
    private static Profile userProfile;

    public static void update(String accessToken, String userId) {
        AuthInfo.accessToken = accessToken;
        AuthInfo.userId = userId;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getUserId() {
        return userId;
    }

    public static Profile getUserProfile() {
        return userProfile;
    }

    public static void setUserProfile(Profile userProfile) {
        AuthInfo.userProfile = userProfile;
    }

    public static boolean isLoggedIn() {
        return accessToken != null;
    }

    public static void clear() {
        accessToken = null;
        userId = null;
        userProfile = null;
    }
}
