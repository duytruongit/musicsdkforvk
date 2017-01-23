package com.lopei.musicsdk.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.lopei.musicsdk.model.AudioTrack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by alan on 15.12.16.
 */

public class AudioParser {
    public static ArrayList<AudioTrack> parseAudio(String html) {
        ArrayList<AudioTrack> audioTracks = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(html, JsonArray.class);
        Set<Map.Entry<String, JsonElement>> entries = jsonArray.get(3).getAsJsonArray().get(0).getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            audioTracks.add(new AudioTrack(entry.getValue().getAsJsonArray()));
        }
        return audioTracks;
    }

    public static ArrayList<AudioTrack> parseAudioNoJson(String html) {
        Document document = Jsoup.parse(html.replace("\n", "").replace("\\", ""));
        Elements elements = document.getElementsByClass("audio_item ai_has_btn");

        ArrayList<AudioTrack> audioTracks = new ArrayList<>();
        for (Element element : elements) {
            audioTracks.add(new AudioTrack(element));
        }
        return audioTracks;
    }

}
