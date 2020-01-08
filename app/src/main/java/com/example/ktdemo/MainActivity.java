package com.example.ktdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ktdemo.dao.AnswerDaoImpl;
import com.example.ktdemo.dao.QuestionDAOImpl;
import com.example.ktdemo.model.Answer;
import com.example.ktdemo.model.Question;
import com.facebook.stetho.Stetho;
import com.ramotion.fluidslider.FluidSlider;

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
    private Button btnVoting;
    private Button btnLogin;
    private TextView tvProgress;
    private Question actualQuestion;


    @Override
    protected void onStart() {
        super.onStart();
        answers = answerDao.getAllAnswer();
        seekbHappy.setProgress(5);
        tvProgress.setText("");
        getRandomQuestion();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnVoting = findViewById(R.id.btnGo);
        seekbHappy = findViewById(R.id.seekbarHappy);
        //ivHappyMeter=(ImageView)findViewById(R.id.ivHappyMeter);
        //tvProgress=findViewById(R.id.tvProgress);
        answerDao = new AnswerDaoImpl(this);
        Stetho.initializeWithDefaults(this);
        tvProgress = findViewById(R.id.tvHappyValue);


        final int rate = 0;


        btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), QuestionListActivity.class);
            startActivity(i);
        });

        seekbHappy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvProgress.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        btnVoting.setOnClickListener(v -> {

            if (tvProgress.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Mozgasd a csúszkát az értékeléshez!", Toast.LENGTH_LONG).show();
            } else if (actualQuestion != null) {
                int rate1 = seekbHappy.getProgress();
                String date = getDateTime();
                int questionId = actualQuestion.getId();
                Answer a = new Answer(rate1, date, questionId);
                answerDao.saveAnswer(a);
                actualQuestion.answers.add(a);
                dao.saveQuestion(actualQuestion);

                btnVoting.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnVoting.setEnabled(true);
                    }
                },5000);
                onStart();
            } else {
                Toast.makeText(getApplicationContext(), "Sajnáljuk, a rendszer karbantartás alatt.", Toast.LENGTH_LONG).show();
            }
        });


    }


    private void getRandomQuestion() {
        tvQuestion = findViewById(R.id.tvQuestion);
        dao = new QuestionDAOImpl(this);
        questions = dao.getAllQuestion();
        List<Question> activeQuestions = getActiveQuestions(questions);
        int sum = getAllWeight(activeQuestions);

        int randomIndex = -1;
        double random = Math.random() * sum;
        for (int i = 0; i < activeQuestions.size(); ++i) {
            random -= activeQuestions.get(i).getWeight();
            if (random <= 0.0d) {
                randomIndex = i;
                break;
            }
        }
        String write = "";
        if (!activeQuestions.isEmpty()) {
            actualQuestion = activeQuestions.get(randomIndex);
            write = actualQuestion.getQuest();
        } else {
            write = "-";
        }
        tvQuestion.setText(write);


    }

    private List<Question> getActiveQuestions(List<Question> questions) {
        List<Question> actives = new ArrayList<>();
        questions.forEach((Question q) -> {
            if (q.isActive()) {
                actives.add(q);
            }
        });
        return actives;
    }

    private int getAllWeight(List<Question> items) {
        int sum = 0;
        if (items != null) {
            for (Question q : items) {
                if (q.isActive()) {
                    sum += q.getWeight();
                }
            }
        }
        return sum;
    }




    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
