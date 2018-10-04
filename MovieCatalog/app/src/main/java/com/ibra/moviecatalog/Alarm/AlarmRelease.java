package com.ibra.moviecatalog.Alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.ibra.moviecatalog.BuildConfig;
import com.ibra.moviecatalog.MainActivity;
import com.ibra.moviecatalog.R;
import com.ibra.moviecatalog.api.MovieClient;
import com.ibra.moviecatalog.api.MovieInterface;
import com.ibra.moviecatalog.model.Movie;
import com.ibra.moviecatalog.model.MovieResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ibra.moviecatalog.db.DatabaseContract.BASE_URL;
import static com.ibra.moviecatalog.db.DatabaseContract.EXTRA_MESSAGE_RECEIVE;
import static com.ibra.moviecatalog.db.DatabaseContract.EXTRA_TYPE_RECEIVE;
import static com.ibra.moviecatalog.db.DatabaseContract.NOTIFICATIONID;

public class AlarmRelease extends BroadcastReceiver {


    public List<MovieResult> list = new ArrayList<>();


    public AlarmRelease() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        MovieInterface movieInterface = MovieClient.getClient(BASE_URL).create(MovieInterface.class);
        Call<Movie> call = movieInterface.getUpcomingMovie(BuildConfig.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                list = response.body().getMovieResults();
                List<MovieResult> items = response.body().getMovieResults();
                int index = new Random().nextInt(items.size());
                MovieResult item = items.get(index);
                int notifId = 503;
                String title = items.get(index).getmTitle();
                String message = items.get(index).getmOverview();
                showNotification(context, title, message, notifId);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("getUpComingMovie", "onFailure: " + t.toString());

            }
        });
    }



    private void showNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(uriTone);
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    public void setReminder(Context context, String type, String time, String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmRelease.class);
        intent.putExtra(EXTRA_MESSAGE_RECEIVE, message);
        intent.putExtra(EXTRA_TYPE_RECEIVE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATIONID, intent, 0);
        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, R.string.on_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmRelease.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATIONID, intent, 0);
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, R.string.off_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }
}




