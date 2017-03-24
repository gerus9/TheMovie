package com.gerus.themovie.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by gerus-mac on 23/03/17.
 */
public class Genre {

    public static final String ID_DB = "id_Genre";

    @DatabaseField()
    private String type;

    @DatabaseField(id = true, columnName = ID_DB)
    private int id;

    @DatabaseField()
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
