package com.gerus.themovie.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gerus.themovie.R;
import com.gerus.themovie.adapters.MiniatureAdapter;
import com.gerus.themovie.interfaces.OnDetailDialogInterface;
import com.gerus.themovie.interfaces.OnMiniatureRecyclerInterface;
import com.gerus.themovie.interfaces.OnWebTasksInterface;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.TV;
import com.gerus.themovie.utils.UNetwork;
import com.gerus.themovie.views.dialogs.DialogFilter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gerus-mac on 23/03/17.
 */

public class SeriesFragment extends GeneralFragment<TV> implements OnMiniatureRecyclerInterface<TV>, OnWebTasksInterface.ListResult<TV> {

    public static final String TAG = SeriesFragment.class.getSimpleName();

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int TIMER_LOADER = 1000;
    private static final int TIMER_ANIMATION = 1500;

    private static SeriesFragment mInstance = null;
    private boolean mFooterLoading = true;
    private MiniatureAdapter mAdapter;
    private static int PAGE = 1;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_series;
    }

    @Override
    protected void searchDialog() {
        new DialogFilter(getActivity(), TAG, new OnDetailDialogInterface() {
            @Override
            public void onItemSelected(Detail poDetail) {
                mListener.onItemSelected(poDetail);
            }
        });
    }

    public SeriesFragment() {
    }

    public static SeriesFragment getInstance() {
        if (mInstance == null) {
            mInstance = new SeriesFragment();
        }
        return mInstance;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prcInitRecyclerView();
        prcSetSwipeRefresh();

        if(mListMiniatures.isEmpty()){
            if (UNetwork.isOnline(mContext)) {
                prcWebGetGeners();
            } else {
                prcUpdateRecyclerView(mDB.getListTV(), false);
            }
        }
    }

    private void prcInitRecyclerView() {
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
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // Scroll bottom
                if (dy > 0 && mFooterLoading) {
                    int viItemsHidden, viItemsVisible, viItemTotal;
                    viItemsVisible = mLayoutManager.getChildCount();
                    viItemTotal = mLayoutManager.getItemCount();
                    viItemsHidden = mLayoutManager.findFirstVisibleItemPosition();

                    if ((viItemsVisible + viItemsHidden) >= viItemTotal) {
                        mFooterLoading = false;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                prcWebGetTV(false);
                            }
                        }, TIMER_LOADER);
                    }
                }
            }
        });

        mAdapter = new MiniatureAdapter(mContext, mListMiniatures, this);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setChangeDuration(TIMER_ANIMATION);
        mRecyclerView.setItemAnimator(itemAnimator);
    }


    /**
     * Method to manipulate swipeRefresh
     */
    private void prcSetSwipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (UNetwork.isOnline(mContext)) {
                    prcWebGetGeners();
                } else {
                    prcCleanError(mContext.getString(R.string.error_network));
                }
            }
        });
    }

    /**
     * Method update Recyclerview values
     *
     * @param poTVList
     */
    private void prcUpdateRecyclerView(List<TV> poTVList, boolean pbSave) {
        if (pbSave) mDB.saveTV(poTVList);
        for (int i = 0; i < poTVList.size(); i++) {
            mListMiniatures.add(poTVList.get(i));
            mAdapter.notifyItemChanged(i);
        }
    }

    /**
     * Show error mesage and reset values
     *
     * @param psError
     */
    private void prcCleanError(String psError) {
        mFooterLoading = true;
        //Remove progress bar
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        mSwipeRefreshLayout.setRefreshing(false);
        if (mListener != null) mListener.onShowError(psError);
    }

    /**
     * Method to call Geners and save DB
     */
    private void prcWebGetGeners() {
        mSwipeRefreshLayout.setRefreshing(true);
        mConn.prcGetGenreTV(new OnWebTasksInterface.GenreResult() {
            @Override
            public void onResult(List<Genre> poGenreList) {
                mDB.saveGensTV(poGenreList);
                prcWebGetTV(true);
            }

            @Override
            public void onError(String psErrorMsg) {
                prcCleanError(psErrorMsg);
            }
        });
    }

    /**
     * Get info from server
     *
     * @param pbReset Reset number of Pages
     */
    private void prcWebGetTV(final boolean pbReset) {
        PAGE = (pbReset) ? 1 : PAGE;
        mConn.prcGetTV(PAGE, this);
    }


    @Override
    public void onItemSelected(TV poTV) {
        mListener.onItemSelected(poTV);
    }

    @Override
    public void onRefreshValues(int piType) {
        switch (piType) {
            case MiniatureAdapter.TYPE_POPULAR:
                Collections.sort(mListMiniatures, new Comparator<TV>() {

                    @Override
                    public int compare(TV o1, TV o2) {
                        return ((Double) o2.getPopularity()).compareTo(o1.getPopularity());
                    }
                });
                break;
            case MiniatureAdapter.TYPE_RATED:
                Collections.sort(mListMiniatures, new Comparator<TV>() {

                    @Override
                    public int compare(TV o1, TV o2) {
                        return ((Double) o2.getVote_average()).compareTo(o1.getVote_average());
                    }
                });
                break;
            case MiniatureAdapter.TYPE_UPCOMING:
                Collections.sort(mListMiniatures, new Comparator<TV>() {

                    @Override
                    public int compare(TV o1, TV o2) {
                        return o2.getFirst_air_date().compareTo(o1.getFirst_air_date());
                    }
                });
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResult(List<TV> poTVList, int piPages) {
        mFooterLoading = true;
        mSwipeRefreshLayout.setRefreshing(false);
        if (PAGE > piPages) {
            onError(mContext.getString(R.string.error_limit));
        } else {
            // IF page 1, reset all values
            if (PAGE == 1) {
                mListMiniatures.clear();
                mAdapter.resetType();
                mAdapter.notifyDataSetChanged();
                if (UNetwork.isOnline(mContext)) mDB.clearTableTV();
            }
            PAGE = PAGE + 1;
            prcUpdateRecyclerView(poTVList, true);
        }
    }

    @Override
    public void onError(String psErrorMsg) {
        prcCleanError(psErrorMsg);
    }
}
