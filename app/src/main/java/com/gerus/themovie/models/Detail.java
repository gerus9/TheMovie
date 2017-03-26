package com.gerus.themovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.gerus.themovie.BuildConfig;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerus-mac on 23/03/17.
 */


public class Detail implements Parcelable {

    public static final String ID = Detail.class.getSimpleName();
    public static final String COLUMN_ID = "idDetail";
    public static final String COLUMN_POPULARITY = "popularity";

    public Detail() {
    }

    @DatabaseField(canBeNull = false, id = true, defaultValue = "0", columnName = COLUMN_ID)
    private int id;

    @DatabaseField()
    private String poster_path;

    @DatabaseField()
    private boolean adult;

    @DatabaseField()
    private String overview;

    @DatabaseField()
    private String original_language;

    @DatabaseField()
    private String backdrop_path;

    @DatabaseField(columnName = COLUMN_POPULARITY)
    private double popularity;

    @DatabaseField()
    private int vote_count;

    @DatabaseField()
    private boolean video;

    @DatabaseField()
    private double vote_average;

    private List<Integer> genre_ids;

    public String getPoster_path() {
        return BuildConfig.IMAGE_URL + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {this.original_language = original_language;}

    public String getBackdrop_path() {
        return BuildConfig.IMAGE_URL + backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster_path);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.original_language);
        dest.writeString(this.backdrop_path);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.vote_count);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.vote_average);
        dest.writeList(this.genre_ids);
    }

    protected Detail(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.original_language = in.readString();
        this.backdrop_path = in.readString();
        this.popularity = in.readDouble();
        this.vote_count = in.readInt();
        this.video = in.readByte() != 0;
        this.vote_average = in.readDouble();
        this.genre_ids = new ArrayList<Integer>();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel source) {
            return new Detail(source);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    public String getIdentifier() {
        return null;
    }
}
