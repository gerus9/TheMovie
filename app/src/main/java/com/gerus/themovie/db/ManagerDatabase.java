package com.gerus.themovie.db;

import android.content.Context;

import com.gerus.themovie.models.DB.GenreRelationship;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by gerus-mac on 15/03/17.
 */

public class ManagerDatabase {

    private File mFile;
    public static final String DATABASE_NAME = "theMovie";
    public static final int DATABASE_VERSION = 1;
    private DBHelper dbHelper;


    public static final String TYPE_MUSIC = "music";
    public static final String TYPE_TV = "tv";

    private static ManagerDatabase sInstance;

    private ManagerDatabase(Context context) {
        this.dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        mFile = context.getDatabasePath(DATABASE_NAME);
    }

    public static synchronized ManagerDatabase getInstance(Context context) {
        if (sInstance == null) sInstance = new ManagerDatabase(context);
        return sInstance;
    }

    public boolean checkDataBase() {
        return mFile.exists();
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void clearTableMovies() {
        try {
            TableUtils.clearTable(dbHelper.getConnectionSource(), Movie.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean saveMovies(List<Movie> poListMovies) {
        try {
            for (int i = 0; i < poListMovies.size(); i++) {
                saveMovie(poListMovies.get(i));
                if (poListMovies.get(i).getGenre_ids() != null && poListMovies.get(i).getGenre_ids().size() > 0) {
                    saveGenesRelation(poListMovies.get(i), poListMovies.get(i).getGenre_ids());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void saveGenesRelation(Movie poDetail, List<Integer> poGenreIds) throws SQLException {
        for (int i = 0; i < poGenreIds.size(); i++) {
            dbHelper.getGenreRelationshipDAO().createOrUpdate(new GenreRelationship(poGenreIds.get(i),poDetail));
        }
    }

    private void saveMovie(Movie poMovie) throws SQLException {
        dbHelper.getMovieDAO().createOrUpdate(poMovie);
    }

    public void saveGenesMovies(List<Genre> poGenre) {
        for (int i = 0; i < poGenre.size(); i++) {
            poGenre.get(i).setType(TYPE_MUSIC);
            try {
                dbHelper.getGenre().createOrUpdate(poGenre.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGensTV(List<Genre> poGenre) throws SQLException {
        for (int i = 0; i < poGenre.size(); i++) {
            poGenre.get(i).setType(TYPE_TV);
            dbHelper.getGenre().createOrUpdate(poGenre.get(i));
        }
    }

    //////////////////////////////////////
    ///////// GETTERS ////////////////////
    //////////////////////////////////////


    public List<Genre> getGendersByID(int piIdDetail) {
        List<Genre> poGenres = new ArrayList<>();
        try {
            QueryBuilder<GenreRelationship, String> orderQb = dbHelper.getGenreRelationshipDAO().queryBuilder();
            orderQb.where().eq(GenreRelationship.COLUMN_DETAIL, piIdDetail);
            QueryBuilder<Genre, Integer> voGender = dbHelper.getGenre().queryBuilder();
            poGenres = voGender.join(orderQb).query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return poGenres;
    }

    public List<Genre> getGendersMovies() {
        List<Genre> mGenres = new ArrayList<>();
        try {
            mGenres = dbHelper.getGenre().queryBuilder().orderBy(Genre.COLUMN_NAME, true).where().eq(Genre.COLUMN_TYPE, TYPE_MUSIC).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mGenres;
    }

    public List<Movie> getMoviesByGenders(List<Integer> piIDs) {
        List<Movie> mMovies = new ArrayList<>();
        try {
            QueryBuilder<GenreRelationship, String> orderQb = dbHelper.getGenreRelationshipDAO().queryBuilder();
            orderQb.where().in(GenreRelationship.COLUMN_GENRE, piIDs);
            QueryBuilder<Movie, Integer> voMovies = dbHelper.getMovieDAO().queryBuilder();
            voMovies.orderBy(Detail.COLUMN_TITLE, true);
            voMovies.distinct();
            mMovies = voMovies.join(orderQb).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mMovies;
    }

    public List<Movie> getListMovies() {
        try {
            return dbHelper.getMovieDAO().queryBuilder().orderBy(Movie.COLUMN_POPULARITY, false).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
