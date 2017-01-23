package com.lopei.musicsdk.model;

import java.util.ArrayList;

/**
 * Created by alan on 17.12.16.
 */

public class FriendsResponse {
    private int count;
    private ArrayList<Profile> items;

    public int getCount() {
        return count;
    }

    public ArrayList<Profile> getFriends() {
        return items;
    }
}
