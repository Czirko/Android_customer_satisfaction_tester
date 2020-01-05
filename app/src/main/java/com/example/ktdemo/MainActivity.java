package com.example.ktdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ktdemo.dao.AnswerDaoImpl;
import com.example.ktdemo.dao.QuestionDAOImpl;
import com.example.ktdemo.model.Answer;
import com.example.ktdemo.model.Question;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Question> questions;
    private QuestionDAOImpl dao;
    private List<Answer> answers;
    private AnswerDaoImpl answerDao;
    private TextView tvQuestion;
    private SeekBar seekbHappy;
    private Button btnGo;
    private Button btnLogin;
    private ImageView ivHappyMeter;
    private TextView tvProgress;
    private Question actualQuestion;


    @Override
    protected void onStart() {
        super.onStart();
        answers=answerDao.getAllAnswer();
        getRandomQuestion();

    }


    private void getRandomQuestion() {
        tvQuestion=findViewById(R.id.tvQuestion);
        dao=new QuestionDAOImpl(this);
        questions=dao.getAllQuestion();
        int sum = getAllWeight(questions);

        int randomIndex = -1;
        double random = Math.random() * sum;
        for (int i = 0; i < questions.size(); ++i)
        {
            random -= questions.get(i).getWeight();
            if (random <= 0.0d)
            {
                randomIndex = i;
                break;
            }
        }
        String write="";
        if(!questions.isEmpty()) {
            actualQuestion = questions.get(randomIndex);
            write=actualQuestion.getQuest();
        }else{
            write="Üres az adatbázis";
        }
        tvQuestion.setText(write);




    }
    private int getAllWeight(List<Question> items){
        int sum=0;
        if(items!=null){
        for(Question q: items){
          if(q.isActive()){
              sum+=q.getWeight();
          }
        }}
        return sum;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnGo=findViewById(R.id.btnGo);
        seekbHappy= findViewById(R.id.seekbHappyBar);
        //ivHappyMeter=(ImageView)findViewById(R.id.ivHappyMeter);
        //tvProgress=findViewById(R.id.tvProgress);
        answerDao=new AnswerDaoImpl(this);
        Stetho.initializeWithDefaults(this);

        final int rate=0;




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),QuestionListActivity.class);
                startActivity(i);
            }
        });

        seekbHappy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               //tvProgress.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int rate=seekbHappy.getProgress();
              String date = getDateTime();
              int questionId= actualQuestion.getId();
                Answer a = new Answer(rate,date,questionId);

                log("A mentés előtt+ id: "+a.getId());
                for (Answer ans : actualQuestion.answers){
                    log(ans.getRate()+"");
                }
                answerDao.saveAnswer(a);



                actualQuestion.answers.add(a);
                List<Answer> valaszok=answerDao.getAllAnswer();
                if(valaszok!=null){
                log("data valaszok: ");
                for (Answer ans : valaszok){
                    log(ans.getRate()+"");
                }}else {
                    Log.d("LLLL", "Üres bazmeg");
                }

                dao.saveQuestion(actualQuestion);
                Log.d("LLLL",actualQuestion.getQuest()+" a kérdés");

                onStart();
            }
        });


    }

    private void log(String m){
        Log.d("LLLL", m);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
