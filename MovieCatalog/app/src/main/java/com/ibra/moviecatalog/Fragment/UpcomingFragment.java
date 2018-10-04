package com.ibra.moviecatalog.Fragment;


import android.content.Context;
import android.os.Bundle;
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
import com.ibra.moviecatalog.taskLoader.UpComingAsyncTaskLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    RecyAdapter adapter;
    private ArrayList<MovieItem> upcomData;
    RecyclerView mrecyclerView;
    Context context;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_upcoming, container, false);

       View view = inflater.inflate(R.layout.fragment_upcoming,container,false);
       context = view.getContext();

       mrecyclerView = (RecyclerView)view.findViewById(R.id.upcoming);
       adapter       =  new RecyAdapter(getActivity());
       mrecyclerView.setLayoutManager(new LinearLayoutManager(context));
       mrecyclerView.setAdapter(adapter);

       getLoaderManager().initLoader(0,null,this);
       return view;

    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        return new UpComingAsyncTaskLoader(getContext(),upcomData);
    }

    @Override
    public void onLoadFinished( Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> upcomData) {
        adapter.setData(upcomData);
    }

    @Override
    public void onLoaderReset( Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);

    }
}
