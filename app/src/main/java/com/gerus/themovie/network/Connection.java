package com.gerus.themovie.network;


import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.gerus.themovie.BuildConfig;
import com.gerus.themovie.R;
import com.gerus.themovie.models.network.NetworkModel;
import com.gerus.themovie.utils.UIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gerus-mac on 22/03/17.
 */

public class Connection {

    protected Context mContext;
    protected URL mURL;
    protected HttpURLConnection mConn = null;
    private NetworkModel mNetworkModel;

    public Connection(Context poContext) {
        mContext = poContext;
        mNetworkModel = new NetworkModel(poContext.getString(R.string.error_network));
    }

    private void prcLog(String psLog) {
        if (BuildConfig.LOG) Log.i(this.getClass().getSimpleName(), psLog);
    }


    public NetworkModel sendRequestGET(String psURL, int timeout) {
        try {
            prcLog(psURL);
            prcLog("GET");

            mURL = new URL(psURL);
            setConnect(mURL, timeout);
            mConn.setRequestMethod("GET");

            prcSetValuesInMsgModel();

            return mNetworkModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mNetworkModel;
    }

    public NetworkModel sendRequestPOST(String psURL, String psJSON, int timeout) {

        try {
            prcLog(psURL);
            prcLog("POST");
            prcLog("JSON: " + psJSON);

            mURL = new URL(psURL);
            setConnect(mURL, timeout);
            mConn.setDoOutput(true);
            mConn.setRequestMethod("POST");

            OutputStreamWriter out = new OutputStreamWriter(mConn.getOutputStream());
            out.write(psJSON);
            out.close();

            prcSetValuesInMsgModel();

            return mNetworkModel;
        } catch (Exception e) {
            mNetworkModel.setMessage(e.getMessage());
        }
        return mNetworkModel;
    }

    public NetworkModel sendRequestPUT(String psURL, int timeout, String psJSON) {
        try {
            prcLog(psURL);
            prcLog("PUT");
            mURL = new URL(psURL);
            setConnect(mURL, timeout);

            mConn.setDoOutput(true);
            mConn.setRequestMethod("PUT");

            OutputStreamWriter out = new OutputStreamWriter(mConn.getOutputStream());
            out.close();

            prcSetValuesInMsgModel();

            return mNetworkModel;
        } catch (IOException e) {
            mNetworkModel.setMessage(e.getMessage());
        }
        return mNetworkModel;
    }

    private void prcSetValuesInMsgModel() throws IOException {
        mNetworkModel.setStatusCode(0);
        mNetworkModel.setMessage(mContext.getString(R.string.error_network));

        int viCode = mConn.getResponseCode();

        String vsMsg = UIO.fncsConvertInputToString((viCode >= 200 && viCode < 400) ? mConn.getInputStream() : mConn.getErrorStream());

        prcLog("CODE:" + viCode);
        prcLog("MSG:" + vsMsg);

        mNetworkModel.setStatusCode(viCode);
        mNetworkModel.setMessage(vsMsg);
    }


    //////////////////////////////////////////////////////////////////////
    /////////////////////////////   UTILS        /////////////////////////
    //////////////////////////////////////////////////////////////////////

    public void setConnect(URL urlConnect, int timeOut) throws IOException {
        if (urlConnect.getProtocol().startsWith("https")) {
            mConn = (HttpsURLConnection) urlConnect.openConnection();
        } else {
            mConn = (HttpURLConnection) urlConnect.openConnection();
        }
        mConn.setConnectTimeout(timeOut);
        mConn.setReadTimeout(timeOut);

        mConn.setRequestProperty("Accept", "application/json");
        mConn.setRequestProperty("Content-type", "application/json");
    }

    private String getQuery(List<Pair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Pair pair : params) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(pair.first.toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second.toString(), "UTF-8"));
        }
        return result.toString();
    }


}
