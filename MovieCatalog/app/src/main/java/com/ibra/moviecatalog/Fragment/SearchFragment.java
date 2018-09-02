package com.ibra.moviecatalog.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ibra.moviecatalog.DetailMovie;
import com.ibra.moviecatalog.MainActivity;
import com.ibra.moviecatalog.MovieAdapter;
import com.ibra.moviecatalog.MovieItem;
import com.ibra.moviecatalog.R;
import com.ibra.moviecatalog.taskLoader.MyAsyncTaskLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>{

    ImageView poster_detail;
    ListView listV;
    MovieAdapter adapter;
    EditText ed_search;
    Button btn_search;

    static final String EXTRAS_FILM = "extras_film";


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   return inflater.inflate(R.layout.fragment_search, container, false);

       View view = inflater.inflate(R.layout.fragment_search,container,false);

       adapter = new MovieAdapter(getActivity());
       adapter.notifyDataSetChanged();

       listV = (ListView)view.findViewById(R.id.listV);
       listV.setAdapter(adapter);

       listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MovieItem item = (MovieItem)parent.getItemAtPosition(position);

               Intent intent = new Intent(getContext(), DetailMovie.class);

               intent.putExtra(DetailMovie.EXTRA_POSTER_DETAIL, item.getPoster());
               intent.putExtra(DetailMovie.EXTRA_TITLE, item.getTitle());
               intent.putExtra(DetailMovie.EXTRA_RATE_COUNT, item.getRate_count());
               intent.putExtra(DetailMovie.EXTRA_RATE, item.getRate());
               intent.putExtra(DetailMovie.EXTRA_RELEASE_DATE, item.getReleasedate());
               intent.putExtra(DetailMovie.EXTRA_OVERVIEW, item.getOverview());

               startActivity(intent);
           }
       });


        poster_detail =(ImageView)view.findViewById(R.id.img_detail);
        ed_search = (EditText)view.findViewById(R.id.Ed_search);
        btn_search = (Button)view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(mylistener);

        String title = ed_search.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, title);

        getLoaderManager().initLoader(0,bundle,this);

        return view;
    }

    View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String titleMovie = ed_search.getText().toString();
            if (TextUtils.isEmpty(titleMovie)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_FILM,titleMovie);
            getLoaderManager().restartLoader(0,bundle,SearchFragment.this);

        }
    };


    @NonNull
    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String titleMovie = "";
        if (bundle != null){
            titleMovie = bundle.getString(EXTRAS_FILM);
        }
        return new MyAsyncTaskLoader(getActivity(), titleMovie);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {

        adapter.setdata(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {
        adapter.setdata(null);

    }
}
