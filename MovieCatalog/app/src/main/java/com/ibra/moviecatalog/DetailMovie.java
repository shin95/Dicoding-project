package com.ibra.moviecatalog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovie extends AppCompatActivity {



    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_RATE_COUNT   = "extra_rate_count";
    public static String EXTRA_RATE         = "extra_rate";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_POSTER_DETAIL= "extra_poster_detail";

    private ImageView iv_poster_detail;
    private TextView tv_title_detail,tv_rating_detail,tv_redate_detail,tv_overview_detail;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        iv_poster_detail = (ImageView)findViewById(R.id.img_detail);
        tv_title_detail  = (TextView)findViewById(R.id.tv_title_detail);
        tv_rating_detail = (TextView)findViewById(R.id.tv_rating_detaill);
        tv_redate_detail = (TextView)findViewById(R.id.tv_release_date_detaill);
        tv_overview_detail = (TextView)findViewById(R.id.tv_overview_detaill);

        String poster        = getIntent().getStringExtra(EXTRA_POSTER_DETAIL);
        String title         = getIntent().getStringExtra(EXTRA_TITLE);
        String rating_count  = getIntent().getStringExtra(EXTRA_RATE_COUNT);
        String rate          = getIntent().getStringExtra(EXTRA_RATE);
        String releasedate   = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String overview      = getIntent().getStringExtra(EXTRA_OVERVIEW);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date                       = dateFormat.parse(releasedate);
            SimpleDateFormat newdateformat  = new SimpleDateFormat("yyyy-MM-dd");
            String date_of_release          = newdateformat.format(date);
            tv_redate_detail.setText(date_of_release);

        }catch (ParseException e){
            e.printStackTrace();
        }

        tv_title_detail.setText(title);
        tv_overview_detail.setText(overview);
        tv_rating_detail.setText(rating_count + " Ratings (" + rate + "/10)");


        Glide.with(DetailMovie.this).load("http://image.tmdb.org/t/p/w500/" + poster)
                .override(350,350)
                .into(iv_poster_detail);


    }
}

