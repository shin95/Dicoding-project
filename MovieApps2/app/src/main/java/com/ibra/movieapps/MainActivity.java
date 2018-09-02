package com.ibra.movieapps;

import android.app.LoaderManager;
import android.content.Intent;

import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>
{

    ImageView poster_detail;
    ListView listV;
    MovieAdapter adapter;
    EditText ed_search;
    Button btn_search;

    static final String EXTRAS_FILM = "extras_film";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MovieAdapter(this);
        listV = (ListView)findViewById(R.id.listV);
        listV.setAdapter(adapter);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MovieItem item = (MovieItem)parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, DetailMovie.class);

                intent.putExtra(DetailMovie.EXTRA_POSTER_DETAIL, item.getPoster());
                intent.putExtra(DetailMovie.EXTRA_TITLE, item.getTitle());
                intent.putExtra(DetailMovie.EXTRA_RATE_COUNT, item.getRate_count());
                intent.putExtra(DetailMovie.EXTRA_RATE, item.getRate());
                intent.putExtra(DetailMovie.EXTRA_RELEASE_DATE, item.getReleasedate());
                intent.putExtra(DetailMovie.EXTRA_OVERVIEW, item.getOverview());

                startActivity(intent);

            }
        });

        poster_detail =(ImageView)findViewById(R.id.img_detail);
        ed_search = (EditText)findViewById(R.id.Ed_search);
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(mylistener);


        String title = ed_search.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM,title);

        getLoaderManager().initLoader(0,bundle,this);
    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id,  Bundle args) {

        String titleMovie = "";
        if (args != null){
            titleMovie = args.getString(EXTRAS_FILM);
        }

        return new MyAsyncTaskLoader(this, titleMovie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        adapter.setdata(data);
    }


    @Override
    public void onLoaderReset( Loader<ArrayList<MovieItem>> loader) {
        adapter.setdata(null);
    }

    View.OnClickListener mylistener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            String titleMovie = ed_search.getText().toString();

            if (TextUtils.isEmpty(titleMovie))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_FILM, titleMovie);
            getLoaderManager().restartLoader(0, bundle,MainActivity.this);
        }
    };
}
