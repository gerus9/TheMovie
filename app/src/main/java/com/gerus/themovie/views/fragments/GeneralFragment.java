package com.gerus.themovie.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gerus.themovie.R;
import com.gerus.themovie.interfaces.OnGeneralInterface;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.network.WebTasks;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gerus-mac on 23/03/17.
 */

public abstract class GeneralFragment<T> extends Fragment {

    protected abstract int getIdLayout();
    protected abstract void searchDialog();

    protected ArrayList<T> mListMiniatures = new ArrayList<T>();
    protected WebTasks mWebTasks;
    protected Unbinder mUnbinder;
    protected Context mContext;
    protected OnGeneralInterface mListener;
    private static MoviesFragment instance = null;

    public static GeneralFragment getInstance() {
        if (instance == null) {
            instance = new MoviesFragment();
        }
        return instance;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
        setHasOptionsMenu(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                searchDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View voView = inflater.inflate(getIdLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, voView);
        mContext = getActivity();
        mWebTasks = new WebTasks(mContext);
        return voView;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        if (context instanceof OnGeneralInterface) {
            mListener = (OnGeneralInterface) context;
        } else {
            throw new ClassCastException(getActivity().getClass().getSimpleName() + " must implement "+OnGeneralInterface.class.getSimpleName());
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) mUnbinder.unbind();
    }
}
