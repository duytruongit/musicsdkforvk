package com.lopei.musicsdk;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lopei.musicsdk.model.AudioTrack;
import com.lopei.musicsdk.network.ParsingWebService;
import com.lopei.musicsdk.ui.AuthActivity;
import com.lopei.musicsdk.util.AudioParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alan on 15.12.16.
 */

public class VKMusicSDK {
    public static final String DEFAULT_APP_ID = "5778372";

    public static int REQUEST_CODE = 514;

    private static VKMusicSDK instance;

    public static VKMusicSDK getInstance() {
        if (instance == null) {
            instance = new VKMusicSDK();
        }
        return instance;
    }


    public void logIn(Fragment fragment, @Nullable String appId) {
        Intent intent = new Intent(fragment.getContext(), AuthActivity.class);
        intent.putExtra(Constants.EXTRA_APP_ID, appId);
        intent.putExtra(Constants.EXTRA_LOGOUT, false);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    public void logOut(Fragment fragment) {
        Intent intent = new Intent(fragment.getContext(), AuthActivity.class);
        intent.putExtra(Constants.EXTRA_LOGOUT, true);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }


    public void logIn(Activity activity, String appId) {
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.putExtra(Constants.EXTRA_APP_ID, appId);
        intent.putExtra(Constants.EXTRA_LOGOUT, false);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void logOut(Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.putExtra(Constants.EXTRA_LOGOUT, true);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void searchAudio(String searchQuery,
                            @Nullable final Collection<AudioTrack> filterTracks,
                            int offset,
                            @Nullable final OnAudioLoadedListener onAudioLoadedListener) {
        ParsingWebService.service.searchAudio("1", searchQuery, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onAudioLoadedListener != null) {
                            onAudioLoadedListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (onAudioLoadedListener != null) {
                            try {
                                List<AudioTrack> trackList = AudioParser.parseAudioNoJson(responseBody.string());
                                onAudioLoadedListener.onAudioLoaded(filterTracks(filterTracks, trackList));
                            } catch (IOException e) {
                                e.printStackTrace();
                                onAudioLoadedListener.onError(e);
                            }
                        }
                    }
                });
    }

    public void loadGroupAudio(@Nullable String groupId,
                               @Nullable Collection<AudioTrack> filterTracks,
                               int offset,
                               @Nullable OnAudioLoadedListener onAudioLoadedListener) {
        String id = groupId;
        if (!id.startsWith("-")) {
            id = "-" + id;
        }
        loadUserAudio(id, filterTracks, offset, onAudioLoadedListener);
    }

    public void loadUserAudio(@Nullable String userId,
                              @Nullable final Collection<AudioTrack> filterTracks,
                              int offset,
                              @Nullable final OnAudioLoadedListener onAudioLoadedListener) {
        ParsingWebService.service.loadUserAudioJson(userId, 1, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onAudioLoadedListener != null) {
                            onAudioLoadedListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (onAudioLoadedListener != null) {
                            try {
                                List<AudioTrack> trackList = AudioParser.parseAudio(responseBody.string());
                                onAudioLoadedListener.onAudioLoaded(filterTracks(filterTracks, trackList));
                            } catch (IOException e) {
                                e.printStackTrace();
                                onAudioLoadedListener.onError(e);
                            }
                        }
                    }
                });
    }

    private List<AudioTrack> filterTracks(Collection<AudioTrack> oldTracks,
                                          Collection<AudioTrack> newTracks) {
        if (oldTracks == null || oldTracks.size() == 0) {
            return new ArrayList<>(newTracks);
        }
        List<AudioTrack> filtered = new ArrayList<>();
        for (AudioTrack audioTrack : oldTracks) {
            if (!newTracks.contains(audioTrack)) {
                filtered.add(audioTrack);
            }
        }
        return filtered;
    }

}
