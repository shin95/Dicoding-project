package com.ibra.movieapps;

import org.json.JSONObject;

public class MovieItem {

    private String title;
    private String releasedate;
    private String overview;
    private String poster;
    private String rate;
    private String rate_count;



    public MovieItem (JSONObject object){
        try {

            String title1     = object.getString("title");
            String rlsdate    = object.getString("release_date");
            String overview1  = object.getString("overview");
            String rating       = object.getString("vote_average");
            String rating_count = object.getString("vote_count");
            String poster1    = object.getString("poster_path");

            this.title = title1;
            this.releasedate = rlsdate;
            this.overview = overview1;
            this.rate = rating;
            this.rate_count= rating_count;
            this.poster = poster1;


        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate_count() {
        return rate_count;
    }

    public void setRate_count(String rate_count) {
        this.rate_count = rate_count;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


}
