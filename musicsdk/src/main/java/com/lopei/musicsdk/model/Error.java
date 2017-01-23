package com.lopei.musicsdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alan on 15.12.16.
 */

public class Error {
    @SerializedName("error_code")
    private String name = "";
    @SerializedName("error_msg")
    private String description = "";
    @SerializedName("captcha_sid")
    private String captchaId;
    @SerializedName("captcha_img")
    private String captchaImageUrl;

    public Error(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static String formatErrorText(Error error) {
        return String.format("%s - %s", error.getName(), error.getDescription());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isError() {
        return name != null;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public String getCaptchaImageUrl() {
        return captchaImageUrl;
    }
}
