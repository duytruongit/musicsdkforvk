package com.lopei.musicsdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alan on 15.12.16.
 */

public class Profile extends Page {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}
