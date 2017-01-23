package com.lopei.musicsdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alan on 20.12.16.
 */

public abstract class Page {
    private int id;
    @SerializedName(value = "photo_100", alternate = {"photo_200", "photo_50"})
    private String photo;

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public abstract String getName();
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Page) {
            if (id == ((Page) obj).id) {
                return true;
            }
        }
        return false;
    }

}
