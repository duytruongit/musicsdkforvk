package com.lopei.musicsdk.model;

import java.util.ArrayList;

/**
 * Created by alan on 17.12.16.
 */

public class ResponseArrayWrapper<T> {
    private ArrayList<T> response;

    public ArrayList<T> getResponse() {
        return response;
    }
}