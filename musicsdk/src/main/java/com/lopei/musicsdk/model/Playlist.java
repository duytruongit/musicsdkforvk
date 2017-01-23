package com.lopei.musicsdk.model;

import java.util.ArrayList;

/**
 * Created by alan on 15.12.16.
 */

public class Playlist {
    private ArrayList<AudioTrack> audioTracks;

    public Playlist(ArrayList<AudioTrack> audioTracks) {
        this.audioTracks = audioTracks;
    }

    public ArrayList<AudioTrack> getAudioTracks() {
        return audioTracks;
    }

    public void addAudioTracks(ArrayList<AudioTrack> audioTracks) {
        if (this.audioTracks == null) {
            this.audioTracks = new ArrayList<>();
        }
        this.audioTracks.addAll(audioTracks);
    }
}
