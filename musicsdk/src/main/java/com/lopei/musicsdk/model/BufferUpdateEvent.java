package com.lopei.musicsdk.model;

/**
 * Created by alan on 16.12.16.
 */
public class BufferUpdateEvent {
    private int progress;

    public BufferUpdateEvent(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }
}
