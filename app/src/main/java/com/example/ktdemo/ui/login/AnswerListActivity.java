package com.example.ktdemo.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ktdemo.R;
import com.example.ktdemo.adapter.AnswerRecyclerViewAdapter;
import com.example.ktdemo.model.Answer;
import com.example.ktdemo.model.Question;

import java.util.List;

public class AnswerListActivity extends AppCompatActivity {

    private List<Answer> items;
    private AnswerRecyclerViewAdapter adapter;
    private RecyclerView rcView;
    private TextView tvQuestion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
        tvQuestion=findViewById(R.id.tvAnswerListQuestion);

        rcView=findViewById(R.id.rvAnswerList);
        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Question q = (Question) getIntent().getSerializableExtra("q");
        tvQuestion.setText(q.getQuest());
        items=q.answers;
        adapter=new AnswerRecyclerViewAdapter(items,this);
        rcView.setAdapter(adapter);

    }
}
