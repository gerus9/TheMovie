package com.gerus.themovie.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.gerus.themovie.R;
import com.gerus.themovie.custom.CircleDisplay;
import com.gerus.themovie.custom.CustomDouble;
import com.gerus.themovie.db.ManagerDatabase;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.image) ImageView mImage;
    @BindView(R.id.txt_description) TextView mDescription;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.circleDisplay) CircleDisplay mCircleDisplay;
    @BindView(R.id.customDouble_date) CustomDouble mDate;
    @BindView(R.id.flowLayout) FlowLayout mFlowCategories;

    private Activity mContext;
    private Detail mDetail;
    private ManagerDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mContext = this;
        mDB = ManagerDatabase.getInstance(mContext);

        setSupportActionBar(mToolbar);
        ActionBar voActionBar = getSupportActionBar();
        voActionBar.setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras()!=null){
            mDetail = getIntent().getExtras().getParcelable(Detail.ID);
            if(mDetail !=null){
                voActionBar.setTitle(mDetail.getTitle());
                prcFillData();
            } else {
                finish();
            }
        } else {
            finish();
        }

    }

    private void prcFillData() {

        Glide.with(mContext).load(mDetail.getBackdrop_path()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.dw_shape).error(R.mipmap.ic_launcher).into(mImage);
        mCircleDisplay.setTextSize(mContext.getResources().getDimension(R.dimen.size_text_radio));
        mCircleDisplay.setAnimDuration(1500);
        mCircleDisplay.setValueWidthPercent(35f);
        mCircleDisplay.setDrawText(true);
        mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.showValue((float) (mDetail.getVote_average()*10), 100f, true);

        mDescription.setText(mDetail.getOverview());


        List<Genre> poGenres = mDB.getGendersByID(mDetail.getId());
        for (int i = 0; i < poGenres.size(); i++) {
            final CheckBox rdbtn = new CheckBox(new ContextThemeWrapper(mContext, R.style.Chip_Detail), null, R.style.Chip_Detail);
            rdbtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rdbtn.setText(poGenres.get(i).getName());
            mFlowCategories.addView(rdbtn);
        }

        mDate.setSubtitle(mDetail.getRelease_date());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
