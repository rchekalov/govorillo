package com.govorillo.govorillo.HTTPHelpers;

import android.os.AsyncTask;
import android.util.Log;

import com.govorillo.govorillo.Recognition.GovorilloListener;
import com.govorillo.govorillo.Singleton;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AsyncPost extends AsyncTask<String, Void, String> {
    private String LOG_TAG = "govorillo_debug";

    public AsyncPost(GovorilloListener context) {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        sendSpeech(urls[0], urls[1]);
        return "ok";
    }

    public void sendSpeech(String url, String speech) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("speech", speech));
        params.add(new BasicNameValuePair("client_id", Singleton.getInstance().getDeviceId()));
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            Log.d(LOG_TAG, e.toString());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.d(LOG_TAG, e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(LOG_TAG, e.toString());
            e.printStackTrace();
        }
    }
}