package com.gerus.themovie.models;

import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by gerus-mac on 23/03/17.
 */

@DatabaseTable
public class Movie extends Detail {

    public static final String ID = Movie.class.getSimpleName();

}
