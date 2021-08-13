package com.example.lab9;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;

public class Database extends SQLiteOpenHelper {

    private static final String dbName = "Database";
    private static final int version = 1;

    private static final String tableName = "MEDICINE";
    private final String col1 = "ID";
    private final String col2 = "NAME";
    private final String col3 = "DATE";
    private final String col4 = "TIME";


    public Database(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE MEDICINE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, DATE TEXT NOT NULL, TIME TEXT NOT NULL);";
        db.execSQL(sql);
    }

    boolean insertData(String name, String date, String time, SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        cv.put("NAME", name);
        cv.put("DATE", date);
        cv.put("TIME", time);

        long result = db.insert("MEDICINE", null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
