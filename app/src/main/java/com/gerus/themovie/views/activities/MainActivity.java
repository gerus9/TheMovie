package com.gerus.themovie.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.gerus.themovie.R;
import com.gerus.themovie.interfaces.OnGeneralInterface;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.utils.UIntents;
import com.gerus.themovie.views.fragments.GeneralFragment;
import com.gerus.themovie.views.fragments.MoviesFragment;
import com.gerus.themovie.views.fragments.SeriesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnGeneralInterface {
    private Activity mContext;

    @BindView(R.id.bottom_navigation)
    protected BottomNavigationView mBottonNavigation;
    @BindView(R.id.coordinatorLayout)
    protected CoordinatorLayout mCoordinator;
    @BindView(R.id.appbar)
    protected AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mContext = this;

        mBottonNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                GeneralFragment voFragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_payments:
                        voFragment = MoviesFragment.getInstance();
                        //mBottonNavigation.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red));
                        break;
                    case R.id.menu_numbers:
                        voFragment = SeriesFragment.getInstance();
                        //mBottonNavigation.setBackgroundColor(ContextCompat.getColor(mContext,R.color.yellow));
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragments, voFragment).commit();
                return true;
            }

        });

        mBottonNavigation.findViewById(R.id.menu_payments).performClick();

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                Log.d(" TAG verticalOffset ", "" + verticalOffset);
                if (verticalOffset == 0) {
                    if (mBottonNavigation.getVisibility() != View.VISIBLE) {
                        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, 0);
                        animate.setDuration(300);
                        animate.setFillAfter(true);
                        mBottonNavigation.startAnimation(animate);
                        mBottonNavigation.setVisibility(View.VISIBLE);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (mBottonNavigation.getVisibility() != View.GONE) {
                        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, mBottonNavigation.getHeight());
                        animate.setDuration(300);
                        animate.setFillAfter(true);
                        mBottonNavigation.startAnimation(animate);
                        mBottonNavigation.setVisibility(View.GONE);
                    }
                }
                /*
                if (verticalOffset == 0) {

                    Log.d()appBarLayout.getTotalScrollRange()
                    //mBottonNavigation.clearAnimation();
                    //mBottonNavigation.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(0,0,0,mBottonNavigation.getHeight());
                    animate.setDuration(300);
                    animate.setFillAfter(true);
                    mBottonNavigation.startAnimation(animate);
                    mBottonNavigation.setVisibility(View.VISIBLE);
                } else {
                    //mBottonNavigation.setVisibility(View.GONE);
                    TranslateAnimation animate = new TranslateAnimation(0,0,0,mBottonNavigation.getHeight());
                    animate.setDuration(300);
                    animate.setFillAfter(true);
                    mBottonNavigation.startAnimation(animate);
                    mBottonNavigation.setVisibility(View.GONE);
                }
                */
            }
        });

    }

    @Override
    public void onItemSelected(Detail poItemDetail) {
        startActivity(UIntents.poDetailActivity(mContext, poItemDetail));
    }

    @Override
    public void onShowMsg(String psMsg) {
        Snackbar.make(mCoordinator, psMsg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onShowError(String psError) {
        Snackbar mSnackbar = Snackbar.make(mCoordinator, psError, Snackbar.LENGTH_LONG);
        mSnackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        mSnackbar.show();
    }
}
