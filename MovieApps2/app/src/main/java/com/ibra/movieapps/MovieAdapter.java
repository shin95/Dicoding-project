package com.ibra.movieapps;

import android.content.Context;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieItem> mdata = new ArrayList<>();
    private LayoutInflater minflater;
    private Context context;

    private String final_overview;

    public MovieAdapter(Context context){
        this.context = context;
        minflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public void setdata (ArrayList<MovieItem> items){
        mdata = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item){
        mdata.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mdata.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mdata == null)return 0;
        return mdata.size();
    }

    @Override
    public MovieItem getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView ==  null){
            holder                  =  new ViewHolder();
            convertView             = minflater.inflate(R.layout.list_movie, null);
            holder.iv_poster_list   = (ImageView)convertView.findViewById(R.id.poster_list);
            holder.tv_title_list    = (TextView) convertView.findViewById(R.id.tv_list_detail);
            holder.tv_rdate_list    = (TextView) convertView.findViewById(R.id.tv_list_release_date);
            holder.tv_overview_list = (TextView) convertView.findViewById(R.id.tv_list_overview);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }
            holder.tv_title_list.setText(mdata.get(position).getTitle());
            String overview = mdata.get(position).getOverview();
            if (TextUtils.isEmpty(overview)){
                final_overview = "No Data";
            }else{
                final_overview = overview;
            }

        holder.tv_overview_list.setText(final_overview);
            String datefound = mdata.get(position).getReleasedate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(datefound);
                SimpleDateFormat new_dateformat =new SimpleDateFormat("EEEE, MM dd,yyyy");
                String release_date = new_dateformat.format(date);
                holder.tv_rdate_list.setText(release_date);
            }catch (ParseException e){
                e.printStackTrace();
            }


        Glide.with(context).load("http://image.tmdb.org/t/p/w154/" + mdata.get(position)
             .getPoster()).into(holder.iv_poster_list);


        return convertView;
    }


    public static class ViewHolder{
        ImageView iv_poster_list;
        TextView tv_title_list;
        TextView tv_rdate_list;
        TextView tv_overview_list;
    }
}
