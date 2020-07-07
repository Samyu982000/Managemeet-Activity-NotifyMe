package com.example.dell.managemeet_notifyme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set OnClick Listener
        findViewById(R.id.Set_Notification).setOnClickListener(this);
        findViewById(R.id.Reset_Notification).setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        EditText editText = findViewById(R.id.meeting_details);
        TimePicker timePicker =findViewById(R.id.SetTime);

        //Set notificationId and text
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationId",notificationId);
        intent.putExtra("todo",editText.getText().toString());

        // getBroadcast(context, requestCode, intent, flags)
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm =(AlarmManager) getSystemService(ALARM_SERVICE);

        switch(view.getId()){
            case R.id.Set_Notification:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Create time
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour );
                startTime.set(Calendar.MINUTE, minute );
                startTime.set(Calendar.SECOND, 0 );
                long alarmStartTime = startTime.getTimeInMillis();

                //Set alarm
                //set(type, milliseconds, intent)

                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);


                Toast.makeText(this, "Your meeting is scheduled :) !!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Reset_Notification:

                alarm.cancel(alarmIntent);
                Toast.makeText(this, "The meeting schedule was not saved.", Toast.LENGTH_SHORT).show();
                break;

        }


    }
}
