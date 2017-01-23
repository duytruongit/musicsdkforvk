package com.lopei.musicsdk.model;

import java.util.ArrayList;

/**
 * Created by alan on 17.12.16.
 */

public class GroupsResponse {
    private int count;
    private ArrayList<Group> items;

    public int getCount() {
        return count;
    }

    public ArrayList<Group> getGroups() {
        return items;
    }
}
