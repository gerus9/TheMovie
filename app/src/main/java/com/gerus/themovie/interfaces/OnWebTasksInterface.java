package com.gerus.themovie.interfaces;

import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.models.TV;

import java.util.List;

/**
 * Created by gerus-mac on 14/03/17.
 */

public interface OnWebTasksInterface {

    public interface ListResult<T> {
        void onResult(List<T> poMovieList);
        void onError(String psErrorMsg);
    }

    public interface GenreResult {
        void onResult(List<Genre> poGenreList);
        void onError(String psErrorMsg);
    }

}
