package com.gerus.themovie.views.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.gerus.themovie.R;
import com.gerus.themovie.custom.CircleDisplay;
import com.gerus.themovie.custom.CustomDouble;
import com.gerus.themovie.models.Detail;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.image) ImageView mImage;
    @BindView(R.id.txt_description) TextView mDescription;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.circleDisplay) CircleDisplay mCircleDisplay;
    @BindView(R.id.customDouble_date) CustomDouble mDate;

    private Context mContext;
    private Detail mNewsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mContext = this;

        setSupportActionBar(mToolbar);
        ActionBar voActionBar = getSupportActionBar();
        voActionBar.setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras()!=null){
            mNewsData = getIntent().getExtras().getParcelable(Detail.ID);
            if(mNewsData!=null){
                voActionBar.setTitle(mNewsData.getTitle());
                prcFillData();

            } else {
                finish();
            }
        } else {
            finish();
        }

    }

    private void prcFillData() {

        Glide.with(mContext).load(mNewsData.getBackdrop_path()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.vc_movies).error(R.mipmap.ic_launcher).into(mImage);
        mCircleDisplay.setTextSize(mContext.getResources().getDimension(R.dimen.size_text_radio));
        mCircleDisplay.setAnimDuration(1500);
        mCircleDisplay.setValueWidthPercent(35f);
        mCircleDisplay.setDrawText(true);
        mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.showValue((float) (mNewsData.getVote_average()*10), 100f, true);

        mDescription.setText(mNewsData.getOverview());

        mDate.setSubtitle(mNewsData.getRelease_date());
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
