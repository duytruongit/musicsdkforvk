package com.lopei.musicsdkforvk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lopei.musicsdk.OnAudioLoadedListener;
import com.lopei.musicsdk.VKMusicSDK;
import com.lopei.musicsdk.model.AudioTrack;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKMusicSDK.getInstance().logIn(this, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VKMusicSDK.REQUEST_CODE) {
            VKMusicSDK.getInstance().searchAudio("Bruno Mars", null, 0, new OnAudioLoadedListener() {
                @Override
                public void onAudioLoaded(List<AudioTrack> audioTracks) {
                    audioTracks.size();
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }
}
