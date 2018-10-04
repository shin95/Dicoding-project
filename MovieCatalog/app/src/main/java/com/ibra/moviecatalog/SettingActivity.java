package com.ibra.moviecatalog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.ibra.moviecatalog.Alarm.AlarmPref;
import com.ibra.moviecatalog.Alarm.DailyAlarm;
import com.ibra.moviecatalog.Alarm.AlarmRelease;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.ibra.moviecatalog.db.DatabaseContract.KEY_FIELD_DAILY_REMINDER;
import static com.ibra.moviecatalog.db.DatabaseContract.KEY_FIELD_UPCOMING_REMINDER;
import static com.ibra.moviecatalog.db.DatabaseContract.KEY_HEADER_DAILY_REMINDER;
import static com.ibra.moviecatalog.db.DatabaseContract.KEY_HEADER_UPCOMING_REMINDER;
import static com.ibra.moviecatalog.db.DatabaseContract.TYPE_REMINDER_PREF;
import static com.ibra.moviecatalog.db.DatabaseContract.TYPE_REMINDER_RECIEVE;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.daily_reminder)
    Switch dailyreminder;
    @BindView(R.id.release_Reminder)
    Switch releasereminder;

    public DailyAlarm dailyAlarm;
    public AlarmRelease alarmRelease;
    public AlarmPref alarmPref;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        dailyAlarm = new DailyAlarm();
        alarmRelease = new AlarmRelease();
        alarmPref = new AlarmPref(this);
        setPreference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Settings");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnCheckedChanged(R.id.daily_reminder)
    public  void  setDailyRemind(boolean isChecked){
        editorDailyReminder = sDailyReminder.edit();
        if (isChecked) {
            editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, true);
            editorDailyReminder.commit();
            dailyReminderOn();
        } else {
            editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, false);
            editorDailyReminder.commit();
            dailyReminderOff();
        }
    }

    @OnCheckedChanged(R.id.release_Reminder)
    public void setReleaseRemind(boolean isChecked){
        editorReleaseReminder = sReleaseReminder.edit();
        if (isChecked) {
            editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, true);
            editorReleaseReminder.commit();
            releaseReminderOn();
        } else {
            editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
            editorReleaseReminder.commit();
            releaseReminderOff();
        }
    }

    @OnClick({R.id.local_setting})
    public void onViewClicked(View view) {
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }

    private void  setPreference(){
        sReleaseReminder = getSharedPreferences(KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
        boolean checkSwUpcomingReminder = sReleaseReminder.getBoolean(KEY_FIELD_UPCOMING_REMINDER,false);
        releasereminder.setChecked(checkSwUpcomingReminder);
        boolean checkSwDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        dailyreminder.setChecked(checkSwDailyReminder);

    }

    private void releaseReminderOn() {
        String time = "08:00";
        String message = "Release Movie, please wait come back soon";
        alarmPref.setReminderReleaseTime(time);
        alarmPref.setReminderReleaseMessage(message);
        alarmRelease.setReminder(SettingActivity.this, TYPE_REMINDER_PREF, time, message);
    }

    private void releaseReminderOff() {
        alarmRelease.cancelAlarm(SettingActivity.this);
    }
    private void dailyReminderOn() {
        String time = "07:00";
        String message = "Daily Movie Missing You, please wait come back soon";
        alarmPref.setReminderDailyTime(time);
        alarmPref.setReminderDailyMessage(message);
        dailyAlarm.setDailyReminder(SettingActivity.this, TYPE_REMINDER_RECIEVE, time, message);
    }

    private void dailyReminderOff() {
        dailyAlarm.cancelAlarm(SettingActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}