package com.gerus.themovie.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gerus.themovie.R;
import com.gerus.themovie.adapters.MiniatureAdapter;
import com.gerus.themovie.custom.CircleDisplay;
import com.gerus.themovie.interfaces.OnMiniatureRecyclerInterface;
import com.gerus.themovie.interfaces.OnWebTasksInterface;
import com.gerus.themovie.models.Movie;

import java.util.List;

import butterknife.BindView;

/**
 * Created by gerus-mac on 23/03/17.
 */

public class MoviesFragment extends GeneralFragment<Movie> {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    protected MiniatureAdapter mAdapter;
    private static int PAGE = 1;



    @Override
    protected int getIdLayout() {
        return R.layout.fragment_movies;
    }

    public MoviesFragment() {}


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prcSetReyclerView();
        prcRefreshValues();

        //Adapter ReyclerView
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext,R.color.red));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PAGE = 1;
                prcRefreshValues();
            }
        });

        /*
        rbRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mMovies, new Comparator<Movie>() {

                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                mAdapter.notifyDataSetChanged();
            }

        });

        rbBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mMovies, new Comparator<Movie>() {

                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return ((Double) o2.getVote_average()).compareTo(o1.getVote_average());
                    }
                });
                mAdapter.notifyDataSetChanged();
            }

        });
        */

    }

    /**
     * Get values form server
     */
    private void prcRefreshValues() {
        mWebTasks.prcGetMovies(PAGE, new OnWebTasksInterface.ListResult<Movie>() {

            @Override
            public void onResult(List<Movie> poMovieList) {
                loading = true;
                mListener.onShowMsg("Cargando la lista");
                mSwipeRefreshLayout.setRefreshing(false);
                if(PAGE==1){
                    mListMiniatures.clear();
                }
                PAGE = PAGE+1;
                updateRecyclerView(poMovieList);

            }

            @Override
            public void onError(String psErrorMsg) {
                loading = true;
                mListener.onShowError(psErrorMsg);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateRecyclerView(List<Movie> poMovieList) {

        for (int i = 0; i < poMovieList.size(); i++) {
            mListMiniatures.add(poMovieList.get(i));
            mAdapter.notifyItemChanged(i);
        }
    }

    private void prcSetReyclerView() {
        final GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, getResources().getInteger(R.integer.numberColumns));
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getNumberColumns(position);
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) { //check for scroll down
                    /*
                    Log.v("...", "Final scroll");
                    Log.v("visibleItemCount", ""+visibleItemCount);
                    Log.v("totalItemCount", ""+totalItemCount);
                    Log.v("pastVisiblesItems", ""+pastVisiblesItems);
                    */

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    prcRefreshValues();
                                }
                            },1000);
                        }
                    }
                }
            }
        });

        mAdapter = new MiniatureAdapter(mContext, mListMiniatures, new OnMiniatureRecyclerInterface<Movie>() {

            @Override
            public void onItemSelected(Movie poMovie) {
                mListener.onItemSelected(poMovie);
            }

            @Override
            public void onClickEmptyLayout() {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setChangeDuration(1500);
        mRecyclerView.setItemAnimator(itemAnimator);

    }

}
