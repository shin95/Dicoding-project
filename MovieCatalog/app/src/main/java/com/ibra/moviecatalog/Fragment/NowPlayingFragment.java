package com.ibra.moviecatalog.Fragment;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibra.moviecatalog.model.MovieItem;
import com.ibra.moviecatalog.R;
import com.ibra.moviecatalog.Adapter.RecyAdapter;
import com.ibra.moviecatalog.taskLoader.NowPlayAsyncTaskLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {


    RecyAdapter adapter;
    private ArrayList<MovieItem> nowpData;
    RecyclerView mrecyclerView;
    Context context;


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        context = view.getContext();
        //  return inflater.inflate(R.layout.fragment_now_playing, container, false);

        mrecyclerView = (RecyclerView) view.findViewById(R.id.nowplaying);

        adapter = new RecyAdapter(getActivity());
        mrecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mrecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        return new NowPlayAsyncTaskLoader(getContext(),nowpData) ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> nowpData) {
        adapter.setData(nowpData);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }

}
//    @Override
//    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> nowpData) {
//        adapter.setData(nowpData);
//
//    }

//    @Override
//    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
//        adapter.setData(null);
//    }
//}
