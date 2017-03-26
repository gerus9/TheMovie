package com.gerus.themovie.models.DB;

import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.models.TV;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;


/**
 * Created by gerus-mac on 24/03/17.
 */

public class GenreRelationship<T extends Detail> {

    public static final String COLUMN_GENRE = "idGenreGen";
    public static final String COLUMN_DETAIL = "idDetailGen";

    @DatabaseField(id = true, useGetSet = true)
    private String  id;

    @DatabaseField(foreign = true, foreignColumnName = com.gerus.themovie.models.Genre.COLUMN_ID, columnName = COLUMN_GENRE)
    private Genre genre;

    @DatabaseField(foreign = true, foreignColumnName = com.gerus.themovie.models.Detail.COLUMN_ID, columnName = COLUMN_DETAIL)
    private T detail;

    @DatabaseField(foreign = true, foreignColumnName = com.gerus.themovie.models.Detail.COLUMN_ID)
    private Movie movie;

    @DatabaseField(foreign = true, foreignColumnName = com.gerus.themovie.models.Detail.COLUMN_ID)
    private TV tv;

    public GenreRelationship() {}

    public GenreRelationship(int piID, T poDetail) {
        genre = new Genre(piID);
        detail = poDetail;
        if(poDetail instanceof Movie){
            movie = (Movie) poDetail;
        } else if(poDetail instanceof TV){
            tv = (TV) poDetail;
        }
    }

    public String getId() {
        return genre.getId()+"-"+detail.getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public TV getTv() {
        return tv;
    }

    public void setTv(TV tv) {
        this.tv = tv;
    }
}
