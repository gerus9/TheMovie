package com.gerus.themovie.models;

import android.os.Parcel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by gerus-mac on 23/03/17.
 */

@DatabaseTable
public class Movie extends Detail {

    public static final String COLUMN_TITLE = "title";
    public static final String ID = Movie.class.getSimpleName();

    @DatabaseField()
    private String release_date;

    @DatabaseField(columnName = COLUMN_TITLE)
    private String title;

    @DatabaseField()
    private String original_title;

    public Movie() {}

    public String getRelease_date() {
        return release_date;
    }

    @Override
    public String getIdentifier() {
        return title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.release_date);
        dest.writeString(this.title);
        dest.writeString(this.original_title);
    }

    protected Movie(Parcel in) {
        super(in);
        this.release_date = in.readString();
        this.title = in.readString();
        this.original_title = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
