package com.gerus.themovie.interfaces;

import com.gerus.themovie.models.Movie;

/**
 * Created by gerus-mac on 14/03/17.
 */

public interface OnMiniatureRecyclerInterface<T> {

    void onItemSelected(T poMovie);
    void onRefreshValues(int piType);
}
