package com.gerus.themovie.utils;

import android.app.Activity;
import android.content.Intent;

import com.gerus.themovie.models.Detail;
import com.gerus.themovie.views.activities.DetailActivity;

/**
 * Created by gerus-mac on 23/03/17.
 */

public class UIntents {

    public static Intent poDetailActivity(Activity poActivity, Detail poNewsData){
        Intent voIntent = new Intent(poActivity, DetailActivity.class);
        voIntent.putExtra(Detail.ID, poNewsData);
        return voIntent;
    }

}
