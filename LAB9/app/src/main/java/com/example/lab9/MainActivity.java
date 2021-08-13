package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static EditText eName, eDate, eTime;
    private static Button insertBtn, showBtn;
    private static TextView show;
    private static Database db;
    private static SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        sqldb = db.getWritableDatabase();

        initViews();
        insertBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
    }

    void initViews() {
        eName = findViewById(R.id.med);
        eDate = findViewById(R.id.date);
        eTime = findViewById(R.id.time);
        insertBtn = findViewById(R.id.insert);
        showBtn = findViewById(R.id.showdb);
        show = findViewById(R.id.show);
    }

    int getHour(String time) {
        switch (time.charAt(0)){
            case 'M' : return 9;
            case 'A' : return 13;
            case 'E' : return 17;
            case 'N' : return 20;
        }
        return -1;
    }

    void setAlarm(String name, String date, String time) {
        int hour = getHour(time);

        String[] tokens = date.split("/");
        int date_array[] = new int[3];
        for(int i = 0; i<3; i++) {
            date_array[i] = Integer.parseInt(tokens[i]);
        }

        //int day = Integer.parseInt(date.substring(0,2));
        //int month = Integer.parseInt(date.substring(3,5))-1;
        //int year = Integer.parseInt(date.substring(6,10));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(date_array[2], date_array[1]-1, date_array[0], hour,12, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 3468783, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(insertBtn)) {
            show.setText("");

            String name = eName.getText().toString();
            String date = eDate.getText().toString();
            String time = eTime.getText().toString();

            if (name.isEmpty() || date.isEmpty() || time.isEmpty())
                Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            else {
                boolean res = db.insertData(name, date, time, sqldb);
                if (res) {
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    setAlarm(name, date, time);
                    eName.setText("");
                    eDate.setText("");
                    eTime.setText("");
                }
                else
                    Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.equals(showBtn)) {
            Cursor cursor = sqldb.rawQuery("SELECT * FROM MEDICINE;", null);
            if(cursor.moveToFirst()) {
                show.setText("NAME\t\t\t\t\t\t\t\t\tDATE\t\t\t\t\t\t\t\t\tTIME\n");
                StringBuilder s = new StringBuilder();
                do {
                    String name = cursor.getString(1);
                    String date = cursor.getString(2);
                    String time = cursor.getString(3);
                    s.append(name + "\t\t\t\t\t\t\t\t" + date + "\t\t\t\t\t\t\t" + time + "\n");
                } while (cursor.moveToNext());
                show.append(s.toString());
            }
            else
                show.setText("Database Empty");
        }
    }
}