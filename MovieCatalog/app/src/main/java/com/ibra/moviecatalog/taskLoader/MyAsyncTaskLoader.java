package com.ibra.moviecatalog.taskLoader;

import android.content.Context;

import com.ibra.moviecatalog.model.MovieItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> mdata;
    private boolean mResult = false;


    private String mTitleMovie;


    public MyAsyncTaskLoader(final Context context, String titleMovie) {
        super(context);

        onContentChanged();
        this.mTitleMovie = titleMovie;
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged())
            forceLoad();
        else if (mResult)
            deliverResult(mdata);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItem> data) {
        mdata = data;
        mResult = true;
        super.deliverResult(data);

    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mResult){
            onReleaseResource(mdata);
            mdata = null;
            mResult = false;
        }
    }


    //API themoviedb abdi
    private static final String API_KEY = "d9f7bcde31d4d4036fd805ca62ce29d0";


    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movieitemss = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                API_KEY + "&language=en-US&query=" + mTitleMovie;

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray lists = responseObject.getJSONArray("results");


                    for (int i = 0; i < lists.length(); i++){
                        JSONObject movie = lists.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(movie);
                        movieitemss.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieitemss;
    }



    private void onReleaseResource(ArrayList<MovieItem> mdata){

    }
}
