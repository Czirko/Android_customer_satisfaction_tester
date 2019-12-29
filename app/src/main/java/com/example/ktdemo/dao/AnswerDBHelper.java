package com.example.ktdemo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AnswerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="aswerlist.db" ;
    private static final int DATABASE_VERSION =1 ;


    public AnswerDBHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE answeritem(_id INTEGER PRIMARY KEY AUTOINCREMENT," +"rate INTEGER," + " date TEXT," + "questionId INTEGER)");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE answeritem");
        onCreate(db);

    }
}
