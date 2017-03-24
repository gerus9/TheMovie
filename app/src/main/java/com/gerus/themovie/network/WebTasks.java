package com.gerus.themovie.network;

import android.content.Context;
import android.os.AsyncTask;

import com.gerus.themovie.BuildConfig;
import com.gerus.themovie.interfaces.OnWebTasksInterface;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.models.network.ListRequest;
import com.gerus.themovie.models.network.NetworkModel;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by gerus-mac on 22/03/17.
 */

public class WebTasks {

    private static final String URL = BuildConfig.URL;

    private static final String URL_MOVIES = URL+"/movie/popular?api_key=%s&page=%d";
    private static final String URL_MOVIES_GENRE = URL+"/genre/movie/list?api_key=%s";

    private static final String URL_TV = URL+"/tv/popular?api_key=%s&page=%d";
    private static final String URL_TV_GENRE = URL+"/genre/tv/list?api_key=%s";

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

    public void prcGetMovies(int piPage, final OnWebTasksInterface.ListResult<Movie> poInterface) {
        String vsURL = String.format(URL_MOVIES,BuildConfig.KEY, piPage);
        mAsynkTask = new AsyncTask<String, Void, NetworkModel>() {

            @Override
            protected NetworkModel doInBackground(String ... params) {
                return mConnect.sendRequestGET(params[POSITION_URL], timeoutAsynctask);
            }

            @Override
            protected void onPostExecute(NetworkModel poNetWorkModel) {
                if (poNetWorkModel.getStatusCode() == 200) {
                    try {
                        TypeToken<ListRequest<Movie>> typeToken = new TypeToken<ListRequest<Movie>>() {};
                        ListRequest<Movie> mListRequest = new GsonBuilder().create().fromJson(poNetWorkModel.getMessage(), typeToken.getType());
                        poInterface.onResult(mListRequest.getResults());
                    } catch (Exception e) {
                        poInterface.onError(poNetWorkModel.getMessage());
                    }
                } else {
                    poInterface.onError(poNetWorkModel.getMessage());
                }
            }
        }.execute(vsURL);
    }




}
