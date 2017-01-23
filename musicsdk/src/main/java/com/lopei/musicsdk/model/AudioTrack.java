package com.lopei.musicsdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;

import com.google.gson.JsonArray;

import org.jsoup.nodes.Element;

/**
 * Created by alan on 15.12.16.
 */
public class AudioTrack implements Parcelable {
    public static final Creator<AudioTrack> CREATOR = new Creator<AudioTrack>() {
        // распаковываем объект из Parcel
        public AudioTrack createFromParcel(Parcel in) {
            return new AudioTrack(in);
        }

        public AudioTrack[] newArray(int size) {
            return new AudioTrack[size];
        }
    };
    private String artist;
    private String title;
    private int totalSecs;
    private String url;
    private String id;

    public AudioTrack(JsonArray array) {
        this.artist = array.get(3).getAsJsonPrimitive().getAsString();
        this.title = array.get(4).getAsJsonPrimitive().getAsString();
        this.url = array.get(2).getAsJsonPrimitive().getAsString();
        this.totalSecs = array.get(5).getAsJsonPrimitive().getAsInt();
        this.id = array.get(1).getAsJsonPrimitive().getAsString();
    }

    public AudioTrack(Element element) {
        this.title = buildTitleString(element.getElementsByClass("ai_title").get(0));
        this.artist = buildTitleString(element.getElementsByClass("ai_artist").get(0));
        this.totalSecs = Integer.parseInt(element.getElementsByClass("ai_dur").get(0).attr("data-dur"));
        this.url = element.getElementsByClass("ai_body").get(0).child(2).attr("value");
        this.id = element.attr("id");
    }

    private AudioTrack(Parcel parcel) {
        this.artist = parcel.readString();
        this.title = parcel.readString();
        this.totalSecs = parcel.readInt();
        this.url = parcel.readString();
        this.id = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(artist);
        parcel.writeString(title);
        parcel.writeInt(totalSecs);
        parcel.writeString(url);
        parcel.writeString(id);
    }

    public String getArtist() {
        return artist;
    }

    public String getLength() {
        String time = "";

        int hours = totalSecs / 3600;

        if (hours > 0) {
            time += String.format("%02d:", hours);
        }
        int minutes = (totalSecs % 3600) / 60;
        time += String.format("%02d:", minutes);
        int seconds = totalSecs % 60;
        time += String.format("%02d", seconds);

        return time;
    }

    public String getTitle() {
        return title;
    }

    public Spanned getFullTitle() {
        String fullTitle = String.format("%s - %s", artist, title);
        return Html.fromHtml(fullTitle);
    }

    public String getUrl() {
        return url;
    }

    public int getTotalSecs() {
        return totalSecs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AudioTrack) {
            if (((AudioTrack) obj).id.equals(id)) {
                return true;
            }
            return false;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    private String buildTitleString (Element element) {
        return Html.fromHtml(element.html()).toString();
    }
}
