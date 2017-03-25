package com.gerus.themovie.models;

import android.os.Parcel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerus-mac on 23/03/17.
 */

@DatabaseTable
public class TV extends Detail {

    public static final String COLUMN_NAME = "name";
    public static final String ID = TV.class.getSimpleName();

    @DatabaseField()
    private String first_air_date;

    @DatabaseField(columnName = COLUMN_NAME)
    private String name;

    @DatabaseField()
    private String original_name;

    public TV() {}


    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.first_air_date);
        dest.writeString(this.name);
        dest.writeString(this.original_name);
    }

    protected TV(Parcel in) {
        super(in);
        this.first_air_date = in.readString();
        this.name = in.readString();
        this.original_name = in.readString();
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel source) {
            return new TV(source);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
        }
    };

    @Override
    public String getIdentifier() {
        return name;
    }
}
