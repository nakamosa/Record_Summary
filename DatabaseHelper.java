package com.example.nakao0411.Record_Summary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "baumkuchen.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE baumkuchen (");
        sb.append("id INTEGER PRIMARY KEY,");
        sb.append("photo string,");
        sb.append("date string,");
        sb.append("latitude int,");
        sb.append("longitude int,");
        sb.append("numberLog int,");
        sb.append("numberBoard String");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){

    }
}