package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements async {

    TextView marText;
    private Button btnstart,btnstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marText =  findViewById(R.id.marqueeText);
        btnstart =  findViewById(R.id.buttonstart);
        btnstop =  findViewById(R.id.buttonstop);
        AsyncClass asyncClass = new AsyncClass();

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncClass.doInBackground();
                asyncClass.onProgressUpdate();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marText.setSelected(false);
                asyncClass.onPostExecute("AsyncTask Completed");
            }
        });
    }

    private class AsyncClass extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(String...values){
                super.onProgressUpdate(values);
                Toast.makeText(getApplicationContext(), "Banner is moving",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... strings) {
                marText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                marText.setSelected(true);
                marText.setVisibility(View.VISIBLE);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                marText.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }
        }
    }

