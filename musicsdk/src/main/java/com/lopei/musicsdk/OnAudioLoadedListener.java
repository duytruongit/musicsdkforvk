package com.lopei.musicsdk;

import com.lopei.musicsdk.model.AudioTrack;

import java.util.List;

/**
 * Created by alan on 23.01.17.
 */

public interface OnAudioLoadedListener {
    void onAudioLoaded(List<AudioTrack> audioTracks);
    void onError(Throwable throwable);
}
