package com.gerus.themovie.network;

import android.content.Context;
import android.os.AsyncTask;

import com.gerus.themovie.BuildConfig;

/**
 * Created by gerus-mac on 22/03/17.
 */

public class WebTasks {

    private static final String URL = BuildConfig.URL;
    private static final int POSITION_URL = 0;

    protected AsyncTask mAsynkTask;
    protected Context mContext;
    protected ManagerConnection mConnect;

    protected static final int timeoutAsynctask = 15000;

    public WebTasks(Context context) {
        mConnect = new ManagerConnection(context);
        mContext = context;
    }

    public void cancel() {
        if (mAsynkTask != null) {
            mConnect.mConn.disconnect();
            mAsynkTask.cancel(true);
        }
    }




}
