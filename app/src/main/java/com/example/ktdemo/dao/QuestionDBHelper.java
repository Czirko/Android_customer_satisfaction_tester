package com.example.ktdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class QuestionDBHelper extends SQLiteOpenHelper {
private static final String DATABASE_NAME ="questionlist.db" ;
private static final int DATABASE_VERSION =1 ;

    public QuestionDBHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE questionitem(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +"quest TEXT,"
                 + " weight INTEGER,"
                 + " active INTEGER )");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE questionitem");
        onCreate(db);

    }
}
