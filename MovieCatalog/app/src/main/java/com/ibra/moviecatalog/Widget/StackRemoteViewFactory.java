package com.ibra.moviecatalog.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;
import com.ibra.moviecatalog.Adapter.RecyAdapter;
import com.ibra.moviecatalog.R;
import com.ibra.moviecatalog.model.MovieItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.ibra.moviecatalog.db.DatabaseContract.LINK_IMAGE;
import static com.ibra.moviecatalog.db.DatabaseContract.MovieColums.CONTENT_URI;
import static com.ibra.moviecatalog.db.DatabaseContract.MovieColums.POSTER;



public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mcontext;
    int mAppWidgetId;
    private Cursor cursor;
    private ArrayList<MovieItem> mWidgetItems = new ArrayList<>();


    public StackRemoteViewFactory(Context appContext, Intent intent){
        mcontext = appContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = mcontext.getContentResolver().query(CONTENT_URI, null,null,null,null);

    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        final long token = Binder.clearCallingIdentity();
        cursor = mcontext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                MovieItem favorite = new MovieItem(cursor);
                mWidgetItems.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());

        }
        if (cursor != null) {
            cursor.close();
        }

        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieItem currentMovieFavorite;
        Bundle extras = new Bundle();
        Bitmap bmp = null;
        String rdate = null;
        try {
            currentMovieFavorite = mWidgetItems.get(position);
            bmp = Glide.with(mcontext)
                    .load(LINK_IMAGE + currentMovieFavorite.getPoster()).asBitmap()
                    .error(new ColorDrawable(mcontext.getResources().getColor(R.color.colorPrimaryDark)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            rdate = currentMovieFavorite.getReleasedate();
            extras.putString(RecyAdapter.EXTRA_MOVIE,currentMovieFavorite.getTitle());
        }catch (InterruptedException | ExecutionException |IndexOutOfBoundsException e){
            Log.d("Widget Load Error", "error");
        }

        RemoteViews rv = new RemoteViews(mcontext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.poster_widget, bmp);
        rv.setTextViewText(R.id.tv_title_widget, rdate);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.poster_widget, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
