package com.example.ktdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ktdemo.model.Answer;
import com.example.ktdemo.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAOImpl implements QuestionDao {

    private QuestionDBHelper helper;
    private SQLiteDatabase db;
    private String dbName = "questionitem";
    private List<Answer> answers;
    private AnswerDaoImpl aDao;
    private Context context;

    public QuestionDAOImpl(Context context) {
        this.helper = new QuestionDBHelper(context);
        this.context=context;
    }

    @Override
    public List<Question> getAllQuestion() {
        db = helper.getReadableDatabase();
        aDao=new AnswerDaoImpl(context);
        answers=aDao.getAllAnswer();

        Cursor c = db.rawQuery("SELECT * FROM " + dbName, null);

        c.moveToFirst();
        List<Question> items = new ArrayList<>();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("_id"));
            String quest = c.getString(c.getColumnIndex("quest"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            boolean active = (c.getInt(c.getColumnIndex("active")) == 1);
            for(Answer a:answers){
                if(a.getQuestionId()==id){

                }
            }

            Question q = new Question(id, quest, weight, active);
            for(Answer a:answers){
                if(a.getQuestionId()==id){
                    q.answers.add(a);
                }
            }
            items.add(q);
            c.moveToNext();
        }

        c.close();
        db.close();
        return items;
    }

    @Override
    public void deleteQuestion(Question q) {

            db = helper.getWritableDatabase();
            db.delete(dbName, "_id=?", new String[]{q.getId() + ""});
            db.close();


    }

    @Override
    public void saveQuestion(Question q) {

            db = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("quest", q.getQuest());
            cv.put("weight", q.getWeight());
            cv.put("active", booleanToInteger(q.isActive()));

            if (q.getId() == -1) {
                long id = db.insert("questionitem", null, cv);
                q.setId((int) id);
            } else {
                db.update(dbName, cv, "_id=?", new String[]{q.getId() + ""});
            }
            db.close();



    }

    private int booleanToInteger(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void deleteAllQuestion() {

            db = helper.getWritableDatabase();
            db.execSQL("DELETE FROM " + dbName);
            db.close();




    }
}
