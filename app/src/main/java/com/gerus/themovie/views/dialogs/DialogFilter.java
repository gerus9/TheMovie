package com.gerus.themovie.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.gerus.themovie.R;
import com.gerus.themovie.adapters.DetailAdapter;
import com.gerus.themovie.adapters.MiniatureAdapter;
import com.gerus.themovie.db.ManagerDatabase;
import com.gerus.themovie.interfaces.OnDetailDialogInterface;
import com.gerus.themovie.interfaces.OnDetailRecyclerInterface;
import com.gerus.themovie.interfaces.OnMiniatureRecyclerInterface;
import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.views.fragments.MoviesFragment;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DialogFilter extends Dialog implements Toolbar.OnMenuItemClickListener {
    private Activity mActivity;
    private ManagerDatabase mDB;
    private String mType;
    private CheckBox[] mCheckBoxes;
    private OnDetailDialogInterface mInterface;
    private List<Detail> mDetails = new ArrayList<Detail>();

    @BindView(R.id.flowLayout) FlowLayout mLinerLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;


    public DialogFilter(Activity poActivity, String psTAG, OnDetailDialogInterface poDialogInterface) {
        super(poActivity, R.style.AppTheme);
        setContentView(R.layout.dialog_filter);
        ButterKnife.bind(this);
        mActivity = poActivity;
        mDB = ManagerDatabase.getInstance(mActivity);
        mInterface = poDialogInterface;
        mToolbar.inflateMenu(R.menu.menu_confirm);
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitle(R.string.search);
        mType = psTAG;
        findByIds();
        show();
    }

    public void findByIds() {

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        List<Genre> mGenreList = (mType.equals(MoviesFragment.TAG) ? mDB.getGendersMovies(): mDB.getGendersTV());
        mCheckBoxes = new CheckBox[mGenreList.size()];
        for (int i = 0; i < mGenreList.size(); i++) {
            final CheckBox rdbtn = new CheckBox(new ContextThemeWrapper(mActivity, R.style.Chip), null, R.style.Chip);
            rdbtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rdbtn.setText(mGenreList.get(i).getName());
            rdbtn.setClickable(true);
            rdbtn.setTag(mGenreList.get(i).getId());
            mCheckBoxes[i] = rdbtn;
            mLinerLayout.addView(rdbtn);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                Log.d("Dialog", " ");
                mDetails.clear();
                List<Integer> voInteger = new ArrayList<>();
                for (int i = 0; i < mCheckBoxes.length; i++) {
                    if (mCheckBoxes[i].isChecked()) {
                        Log.d("Dialog Checked", " " + mCheckBoxes[i].getTag());
                        voInteger.add((Integer) mCheckBoxes[i].getTag());
                    }
                }
                mDetails.addAll((mType.equals(MoviesFragment.TAG)?mDB.getMoviesByGenders(voInteger):mDB.getTVByGenders(voInteger)));

                if (mDetails.size() > 0) {
                    new DialogResult(mActivity, mDetails,mInterface);
                } else {
                    Toast.makeText(mActivity, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }
}