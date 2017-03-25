package com.gerus.themovie.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerus-mac on 23/03/17.
 */
@DatabaseTable
public class Genre {

    public static final String COLUMN_ID = "idGenre";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NAME = "name";

    public Genre() {}

    public Genre(int id) {
        this.id = id;
    }

    @DatabaseField(columnName = COLUMN_TYPE)
    private String type;

    @DatabaseField(canBeNull = false, id = true, columnName = COLUMN_ID)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME)
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
