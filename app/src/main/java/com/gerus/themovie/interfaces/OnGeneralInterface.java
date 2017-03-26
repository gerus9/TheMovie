package com.gerus.themovie.interfaces;

import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Movie;

/**
 * Created by gerus-mac on 23/03/17.
 */

public interface OnGeneralInterface {

    void onItemSelected(Detail poDetail);
    void onShowMsg(String psMsg);
    void onShowError(String psError);

}
