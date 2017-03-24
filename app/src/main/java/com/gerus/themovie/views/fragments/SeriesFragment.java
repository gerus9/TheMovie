package com.gerus.themovie.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerus.themovie.R;
import com.gerus.themovie.models.TV;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gerus-mac on 23/03/17.
 */

public class SeriesFragment extends GeneralFragment<TV> {

    protected Unbinder mUnbinder;
    private static SeriesFragment instance = null;

    public static SeriesFragment getInstance() {
        if (instance == null) {
            instance = new SeriesFragment();
        }
        return instance;
    }

    public SeriesFragment() {}

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_movies;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
