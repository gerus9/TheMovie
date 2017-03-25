package com.gerus.themovie.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gerus.themovie.R;
import com.gerus.themovie.adapters.DetailAdapter;
import com.gerus.themovie.interfaces.OnDetailDialogInterface;
import com.gerus.themovie.interfaces.OnDetailRecyclerInterface;
import com.gerus.themovie.models.Detail;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DialogResult extends Dialog {
    private Activity mActivity;
    private OnDetailDialogInterface mInterface;
    private List<Detail> mDetails = new ArrayList<Detail>();
    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;

    public DialogResult(Activity poActivity, List<Detail> poDetails, OnDetailDialogInterface poInterface) {
        super(poActivity, R.style.AppTheme);
        setContentView(R.layout.dialog_result);
        ButterKnife.bind(this);
        mActivity = poActivity;
        mInterface = poInterface;
        mDetails = poDetails;
        fncoRecyclerView();
        show();
    }


    private void fncoRecyclerView() {

        DetailAdapter mAdapter = new DetailAdapter(mActivity, mDetails, new OnDetailRecyclerInterface() {
            @Override
            public void onItemSelected(Detail poDetail) {
                if(mInterface!=null) mInterface.onItemSelected(poDetail);
            }

        });

        final GridLayoutManager mLayoutManager = new GridLayoutManager(mActivity, mActivity.getResources().getInteger(R.integer.numberColumns));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}