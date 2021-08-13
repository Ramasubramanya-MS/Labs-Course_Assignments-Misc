package com.example.lab_b_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    Button create, open, save, refresh;
    EditText fname, fcontents;

     private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "External Storage Permission not granted", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkPermission())
        {
        } else {
            requestPermission();
        }

        create = findViewById(R.id.create);
        open = findViewById(R.id.open);
        save = findViewById(R.id.save);
        refresh = findViewById(R.id.refresh);

        fname = findViewById(R.id.fname);
        fcontents = findViewById(R.id.fcontents);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = String.valueOf(fname.getText());
                if(filename.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter filename",Toast.LENGTH_SHORT).show();
                }
                else{
                    String messagebody = String.valueOf(fcontents.getText());
                    createfile(filename,messagebody);
                }
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = String.valueOf(fname.getText());
                if(filename.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter filename",Toast.LENGTH_SHORT).show();
                }
                else{
                    String ret = readFromFile(filename);
                    if(ret == ""){
                        Toast.makeText(getApplicationContext(),"Enter Proper Filename",Toast.LENGTH_SHORT).show();
                    }
                    fcontents.setText(ret, TextView.BufferType.EDITABLE);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = String.valueOf(fname.getText());
                if(filename.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter filename",Toast.LENGTH_SHORT).show();
                }
                else{
                    String messagebody = String.valueOf(fcontents.getText());
                    writeToFile(messagebody,filename);
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname.setText("");
                fcontents.setText("");
            }
        });
    }

    public void createfile(String fname, String fcontent) {
        try {
            File root = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            String path = String.valueOf(Environment.getExternalStorageDirectory()) + "/"+fname;
            Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();
            File textfile = new File(root, fname);
            FileWriter writer = new FileWriter(textfile);
            writer.append(fcontent);
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(),"File Created",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFromFile(String fname) {
        String fname1 = Environment.getExternalStorageDirectory()+"/"+fname;
        String ret = "";
        try {
            FileInputStream inputStream = new FileInputStream(new File(fname1));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    void writeToFile(String messagebody,String fname) {
        try {
            File root = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            File textfile = new File(root, fname);
            FileWriter writer = new FileWriter(textfile);
            writer.append(messagebody);
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(),"File Saved",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}