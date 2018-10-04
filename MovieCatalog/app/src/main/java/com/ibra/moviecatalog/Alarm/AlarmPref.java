package com.ibra.moviecatalog.Alarm;

import android.content.Context;
import android.content.SharedPreferences;

import com.ibra.moviecatalog.db.DatabaseContract;

import static com.ibra.moviecatalog.db.DatabaseContract.KEY_REMINDER_Daily;
import static com.ibra.moviecatalog.db.DatabaseContract.KEY_REMINDER_MESSAGE_Daily;
import static com.ibra.moviecatalog.db.DatabaseContract.KEY_REMINDER_MESSAGE_Release;
import static com.ibra.moviecatalog.db.DatabaseContract.PREF_NAME;

public class AlarmPref {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public AlarmPref(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setReminderReleaseTime(String time){
        editor.putString(KEY_REMINDER_Daily,time);
        editor.commit();
    }
    public void setReminderReleaseMessage (String message){
        editor.putString(KEY_REMINDER_MESSAGE_Release,message);
    }
    public void setReminderDailyTime(String time){
        editor.putString(KEY_REMINDER_Daily,time);
        editor.commit();
    }
    public void setReminderDailyMessage(String message){
        editor.putString(KEY_REMINDER_MESSAGE_Daily,message);
    }
}
