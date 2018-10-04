package com.ibra.moviecatalog.taskLoader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ibra.moviecatalog.model.MovieItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.ibra.moviecatalog.BuildConfig.API_KEY;

public class NowPlayAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> mMovieItem;
    private boolean mResult = false;

    public NowPlayAsyncTaskLoader(final Context context, ArrayList<MovieItem> mMovieItem){
        super(context);
        onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged())
            forceLoad();
        else if(mResult)
            deliverResult(mMovieItem);
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        mMovieItem = data;
        mResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if(mResult){
            onReleaseResource(mMovieItem);
            mMovieItem = null;
            mResult    = false;
        }
    }

    private void onReleaseResource(ArrayList<MovieItem> mMovieItem){

    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movie_item = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US";

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
                        movie_item.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movie_item;
    }
}
