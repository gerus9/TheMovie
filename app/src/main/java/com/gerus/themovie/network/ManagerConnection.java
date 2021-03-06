package com.gerus.themovie.network;

import android.content.Context;
import android.os.AsyncTask;

import com.gerus.themovie.BuildConfig;
import com.gerus.themovie.interfaces.OnWebTasksInterface;
import com.gerus.themovie.models.Genre;
import com.gerus.themovie.models.Movie;
import com.gerus.themovie.models.TV;
import com.gerus.themovie.models.network.ListRequest;
import com.gerus.themovie.models.network.NetworkModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by gerus-mac on 22/03/17.
 */

public class ManagerConnection {

    private static final String URL = BuildConfig.URL;

    private static final String URL_MOVIES = URL+"/movie/popular?api_key=%s&page=%d&language=%s";
    private static final String URL_MOVIES_GENRE = URL+"/genre/movie/list?api_key=%s&language=%s";

    private static final String URL_TV = URL+"/tv/popular?api_key=%s&page=%d&language=%s";
    private static final String URL_TV_GENRE = URL+"/genre/tv/list?api_key=%s&language=%s";

    private static final int POSITION_URL = 0;

    protected AsyncTask mAsynkTask;
    protected Context mContext;
    protected Connection mConnect;
    protected String mLanguage =  Locale.getDefault().getLanguage();

    protected static final int timeoutAsynctask = 15000;

    public ManagerConnection(Context context) {
        mConnect = new Connection(context);
        mContext = context;
    }

    public void cancel() {
        if (mAsynkTask != null) {
            try{
                mConnect.mConn.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                mAsynkTask.cancel(true);
            }
        }
    }

    /////////////////////////////////
    ///////////  MOVIES  ///////////
    /////////////////////////////////

    public void prcGetMovies(int piPage, final OnWebTasksInterface.ListResult<Movie> poInterface) {
        String vsURL = String.format(URL_MOVIES,BuildConfig.KEY, piPage, mLanguage);

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
                        poInterface.onResult(mListRequest.getResults(), mListRequest.getTotal_pages());
                    } catch (Exception e) {
                        e.printStackTrace();
                        poInterface.onError(poNetWorkModel.getMessage());
                    }
                } else {
                    poInterface.onError(poNetWorkModel.getMessage());
                }
            }
        }.execute(vsURL);
    }

    public void prcGetGenreMovies(final OnWebTasksInterface.GenreResult poInterface) {
        String vsURL = String.format(URL_MOVIES_GENRE,BuildConfig.KEY,mLanguage);
        mAsynkTask = new AsyncTask<String, Void, NetworkModel>() {

            @Override
            protected NetworkModel doInBackground(String ... params) {
                return mConnect.sendRequestGET(params[POSITION_URL], timeoutAsynctask);
            }

            @Override
            protected void onPostExecute(NetworkModel poNetWorkModel) {
                if (poNetWorkModel.getStatusCode() == 200) {
                    try {
                        Type listType = new TypeToken<ArrayList<Genre>>(){}.getType();
                        JSONObject poJsonObject = new JSONObject(poNetWorkModel.getMessage());
                        String psText = poJsonObject.getString("genres");
                        ArrayList<Genre> poGenres = new Gson().fromJson(psText, listType);
                        poInterface.onResult(poGenres);
                    } catch (Exception e) {
                        e.printStackTrace();
                        poInterface.onError(poNetWorkModel.getMessage());
                    }
                } else {
                    poInterface.onError(poNetWorkModel.getMessage());
                }
            }
        }.execute(vsURL);
    }

    /////////////////////////////////
    ///////////  TV       ///////////
    /////////////////////////////////

    public void prcGetTV(int piPage, final OnWebTasksInterface.ListResult<TV> poInterface) {
        String vsURL = String.format(URL_TV,BuildConfig.KEY, piPage, mLanguage);

        mAsynkTask = new AsyncTask<String, Void, NetworkModel>() {

            @Override
            protected NetworkModel doInBackground(String ... params) {
                return mConnect.sendRequestGET(params[POSITION_URL], timeoutAsynctask);
            }

            @Override
            protected void onPostExecute(NetworkModel poNetWorkModel) {
                if (poNetWorkModel.getStatusCode() == 200) {
                    try {
                        TypeToken<ListRequest<TV>> typeToken = new TypeToken<ListRequest<TV>>() {};
                        ListRequest<TV> mListRequest = new GsonBuilder().create().fromJson(poNetWorkModel.getMessage(), typeToken.getType());
                        poInterface.onResult(mListRequest.getResults(), mListRequest.getTotal_pages());
                    } catch (Exception e) {
                        e.printStackTrace();
                        poInterface.onError(poNetWorkModel.getMessage());
                    }
                } else {
                    poInterface.onError(poNetWorkModel.getMessage());
                }
            }
        }.execute(vsURL);
    }

    public void prcGetGenreTV(final OnWebTasksInterface.GenreResult poInterface) {
        String vsURL = String.format(URL_TV_GENRE,BuildConfig.KEY, mLanguage);
        mAsynkTask = new AsyncTask<String, Void, NetworkModel>() {

            @Override
            protected NetworkModel doInBackground(String ... params) {
                return mConnect.sendRequestGET(params[POSITION_URL], timeoutAsynctask);
            }

            @Override
            protected void onPostExecute(NetworkModel poNetWorkModel) {
                if (poNetWorkModel.getStatusCode() == 200) {
                    try {
                        Type listType = new TypeToken<ArrayList<Genre>>(){}.getType();
                        JSONObject poJsonObject = new JSONObject(poNetWorkModel.getMessage());
                        String psText = poJsonObject.getString("genres");
                        ArrayList<Genre> poGenres = new Gson().fromJson(psText, listType);
                        poInterface.onResult(poGenres);
                    } catch (Exception e) {
                        e.printStackTrace();
                        poInterface.onError(poNetWorkModel.getMessage());
                    }
                } else {
                    poInterface.onError(poNetWorkModel.getMessage());
                }
            }
        }.execute(vsURL);
    }


}
