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
import android.widget.Toast;

import com.gerus.themovie.R;
import com.gerus.themovie.adapters.MiniatureAdapter;
import com.gerus.themovie.custom.CircleDisplay;
import com.gerus.themovie.interfaces.OnDetailDialogInterface;
import com.gerus.themovie.interfaces.OnMiniatureRecyclerInterface;
import com.gerus.themovie.interfaces.OnWebTasksInterface;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.utils.UNetwork;
import com.gerus.themovie.views.dialogs.DialogFilter;

import java.util.Collections;
import java.util.Comparator;
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

    @Override
    protected void searchDialog() {
        new DialogFilter(getActivity(), new OnDetailDialogInterface() {
            @Override
            public void onItemSelected(Detail poDetail) {
                mListener.onItemSelected(poDetail);
            }
        });
    }

    public MoviesFragment() {}


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prcSetReyclerView();

        //Adapter ReyclerView
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext,R.color.red));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(UNetwork.isOnline(mContext)){
                    prcGenre();
                } else {
                    prcCleanError(mContext.getString(R.string.error_network));
                }
            }
        });

        if(UNetwork.isOnline(mContext)){
            prcGenre();
        } else {
            updateRecyclerView(mDB.getListMovies());
        }

    }

    private void prcCleanError(String psError) {
        loading = true;
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        mListener.onShowError(psError);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void prcGenre() {

        mWebTasks.prcGetGenreMovies(new OnWebTasksInterface.GenreResult() {
            @Override
            public void onResult(List<Genre> poGenreList) {
                mDB.saveGenesMovies(poGenreList);
                prcRefreshValues(true);
            }

            @Override
            public void onError(String psErrorMsg) {
                prcCleanError(psErrorMsg);
            }
        });
    }

    /**
     * Get values form server
     */
    private void prcRefreshValues(final boolean pbReset) {
        PAGE  = (pbReset) ? 1 : PAGE;
        mWebTasks.prcGetMovies(PAGE, new OnWebTasksInterface.ListResult<Movie>() {

            @Override
            public void onResult(List<Movie> poMovieList, int piPages) {

                loading = true;
                mSwipeRefreshLayout.setRefreshing(false);
                if(PAGE > piPages){
                    onError(mContext.getString(R.string.error_limit));
                } else {
                    if(pbReset || PAGE == 1){
                        mListMiniatures.clear();
                        mAdapter.resetType();
                        mAdapter.notifyDataSetChanged();
                        mDB.clearTableMovies();
                    }
                    PAGE = PAGE + 1;
                    updateRecyclerView(poMovieList);
                }


            }

            @Override
            public void onError(String psErrorMsg) {
                prcCleanError(psErrorMsg);
            }
        });
    }

    private void updateRecyclerView(List<Movie> poMovieList) {
        mDB.saveMovies(poMovieList);
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

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    prcRefreshValues(false);
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
            public void onRefreshValues(int piType) {
                switch (piType){
                    case MiniatureAdapter.TYPE_POPULAR:
                        Collections.sort(mListMiniatures, new Comparator<Movie>() {

                            @Override
                            public int compare(Movie o1, Movie o2) {
                                return ((Double) o2.getPopularity()).compareTo(o1.getPopularity());
                            }
                        });
                        break;
                    case MiniatureAdapter.TYPE_RATED:
                        Collections.sort(mListMiniatures, new Comparator<Movie>() {

                            @Override
                            public int compare(Movie o1, Movie o2) {
                                return ((Double) o2.getVote_average()).compareTo(o1.getVote_average());
                            }
                        });
                        break;
                    case MiniatureAdapter.TYPE_UPCOMING:
                        Collections.sort(mListMiniatures, new Comparator<Movie>() {

                            @Override
                            public int compare(Movie o1, Movie o2) {
                                return o2.getRelease_date().compareTo(o1.getRelease_date());
                            }
                        });
                        break;
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setChangeDuration(1500);
        mRecyclerView.setItemAnimator(itemAnimator);
    }

}
