package com.example.ktdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ktdemo.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDaoImpl implements AnswerDao {

    private AnswerDBHelper helper;
    private SQLiteDatabase db;

    public AnswerDaoImpl(Context context) {
        this.helper = new AnswerDBHelper(context);
    }

    @Override
    public List<Answer> getAllAnswer() {
        db=helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM answeritem",null);
        c.moveToFirst();
        List<Answer> items = new ArrayList<>();
        while(!c.isAfterLast()){
            int id=c.getInt(c.getColumnIndex("_id"));
            int rate=c.getInt(c.getColumnIndex("rate"));
            String date = c.getString(c.getColumnIndex("date"));
            int questionId=c.getInt(c.getColumnIndex("questionId"));

            Answer a = new Answer(id,rate,date,questionId);
            items.add(a);
            c.moveToNext();

        }

        return items;
    }

    @Override
    public void deleteAnswer(Answer a) {
        db=helper.getWritableDatabase();
        db.delete("answeritem","_id",new String[]{a.getId()+""});
        db.close();

    }

    @Override
    public void saveAnswer(Answer a) {
        Log.d("LLLL","itt mentjük elvileg");
        db=helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rate",a.getRate());
        cv.put("date",a.getDate());
        cv.put("questionId",a.getQuestionId());
        Log.d("LLLL","daomentés");

        if(a.getId()==-1){
            Log.d("LLLL", "daomentés új");
            long id = db.insert("answeritem",null,cv);
            a.setId((int)id);
        }else{
            db.update("answeritem",cv,"_id",new String[]{a.getId()+""});
        }
        db.close();


    }

    @Override
    public void deleteAllAnswer() {
        db=helper.getWritableDatabase();
        db.execSQL("DELETE FROM answeritem");
        db.close();
    }
}
