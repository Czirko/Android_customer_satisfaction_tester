package com.example.ktdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ktdemo.model.Question;

import java.io.Serializable;

public class NewQuestionActivity extends AppCompatActivity {
    private EditText etQuest;
    private EditText etWeight;
    private Switch svActive;
    private TextView tvisActive;
    private Intent intent;
    private Question q;

    public NewQuestionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        etQuest=findViewById(R.id.etQuestionQuest);
        etWeight=findViewById(R.id.etQuestionWeight);
        svActive=findViewById(R.id.swQuestionActive);
        tvisActive=findViewById(R.id.tvIsactive);

        intent=getIntent();
        q=(Question) intent.getSerializableExtra("q");
        if(q!=null){
            etQuest.setText(q.getQuest());
            etWeight.setText(q.getWeight()+"");
            svActive.setChecked(q.isActive());
        }





    }

    public void ok(View view) {
        if(q==null){
            q=new Question();
        }
        q.setQuest(etQuest.getText().toString());
        q.setWeight(Integer.parseInt(etWeight.getText().toString()));
        q.setActive(svActive.isChecked());

        intent.putExtra("q",  q);
        setResult(RESULT_OK,intent);
        Log.d("LLLL","OK method");
        finish();



    }

    private int stringToInt(String s){
        return Integer.parseInt(s);
    }

    public void megse(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
