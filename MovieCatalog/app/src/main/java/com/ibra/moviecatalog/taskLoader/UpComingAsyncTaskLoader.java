package com.ibra.moviecatalog.taskLoader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ibra.moviecatalog.MovieItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.ibra.moviecatalog.BuildConfig.API_KEY;

public class UpComingAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> mData;
    private boolean mResult = false;

    public UpComingAsyncTaskLoader(final Context context, ArrayList<MovieItem> mData) {
        super(context);
        onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        mData = data;
        mResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if (mResult){
            onReleaseResource(mData);
            mData = null;
            mResult = false;
        }
    }

    private void onReleaseResource(ArrayList<MovieItem> mData){

    }


    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movie_items = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";

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
                        MovieItem movieItemss = new MovieItem(movie);
                        movie_items.add(movieItemss);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            }
        });

        return movie_items;
    }
}
