package com.gerus.themovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gerus.themovie.models.DB.GenreRelationship;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.models.TV;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by gerus-mac on 15/03/17.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private Context mContext;

    public DBHelper(Context context) {
        super(context, ManagerDatabase.DATABASE_NAME, null, ManagerDatabase.DATABASE_VERSION);
        mContext = context;
    }

    private Dao<Genre, Integer> genreDAO;
    private Dao<GenreRelationship, String> genreRelationshipDAO;
    private Dao<Movie, Integer> movieDAO;
    private Dao<TV, Integer> tvDAO;

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Genre.class);
            TableUtils.createTable(connectionSource, GenreRelationship.class);
            TableUtils.createTable(connectionSource, Movie.class);
            TableUtils.createTable(connectionSource, TV.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    protected Dao<Genre, Integer> getGenre() throws SQLException {
        if (genreDAO == null)
            genreDAO = getDao(Genre.class);
        return genreDAO;
    }

    protected Dao<GenreRelationship, String> getGenreRelationshipDAO() throws SQLException {
        if (genreRelationshipDAO == null)
            genreRelationshipDAO = getDao(GenreRelationship.class);
        return genreRelationshipDAO;
    }

    protected Dao<Movie, Integer> getMovieDAO() throws SQLException {
        if (movieDAO == null)
            movieDAO = getDao(Movie.class);
        return movieDAO;
    }

    protected Dao<TV, Integer> getTvDAO() throws SQLException {
        if (tvDAO == null)
            tvDAO = getDao(TV.class);
        return tvDAO;
    }



}
