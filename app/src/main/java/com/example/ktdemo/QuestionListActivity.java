package com.example.ktdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.ktdemo.adapter.QuestionRecyclerViewAdapter;
import com.example.ktdemo.dao.QuestionDAOImpl;
import com.example.ktdemo.dao.QuestionDao;
import com.example.ktdemo.model.Question;
import com.example.ktdemo.ui.login.AnswerListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class QuestionListActivity extends AppCompatActivity {

    private static final int REQC_NEW = 1;
    private static final int REQC_EDIT = 2;

    private List<Question> questions;
    private RecyclerView rcView;
    private RecyclerView.Adapter rcAdapter;
    private QuestionDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //questions=getTestList(); //csak a teszt kedvéért
        dao = new QuestionDAOImpl(this);
        questions = dao.getAllQuestion();
        rcView = (RecyclerView)findViewById(R.id.recViewQuestion);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcAdapter = new QuestionRecyclerViewAdapter(questions, this);
        rcView.setAdapter(rcAdapter);
        rcView.setHasFixedSize(true);



        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewQuestionActivity.class);
                startActivityForResult(i, REQC_NEW);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menulist_add_question:
                Intent i = new Intent(getApplicationContext(), NewQuestionActivity.class);
                startActivityForResult(i, REQC_NEW);
                return true;

            case R.id.menulist_delete_all_question:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Törlési megerősítés")
                        .setMessage(getString(R.string.biztosan_tori_az_osszeset))
                        .setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });
                builder.setPositiveButton("Törlés mind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAll();
                    }
                });
                builder.create().show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteAll(){
        dao.deleteAllQuestion();
        questions.clear();
        rcAdapter = new QuestionRecyclerViewAdapter(questions, this);
        rcView.setAdapter(rcAdapter);
    }

    public void log(String msg) {
        Log.d("LLLL", msg);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int index = item.getGroupId();
        Question q = questions.get(index);

        switch (item.getItemId()) {
            case 119:
                String s = "A lista mérete: " + q.answers.size() + "";
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AnswerListActivity.class);
                intent.putExtra("q", q);
                startActivity(intent);
                return true;

            case 120:
                Intent i = new Intent(getApplicationContext(), NewQuestionActivity.class);
                i.putExtra("q", q);
                i.putExtra("index", item.getGroupId());
                startActivityForResult(i, REQC_EDIT);
                return true;

            case 121:
                questions.remove(q);
                dao.deleteQuestion(q);
                rcAdapter.notifyItemRemoved(item.getGroupId());
                return true;

            default:
                return super.onContextItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        log("onactivityforresult" + resultCode + "");
        if (resultCode == RESULT_OK) {
            Question q = (Question) data.getSerializableExtra("q");

            log("RESULTOK");

            if (requestCode == REQC_NEW) {
                log("REQCNEW");
                questions.add(q);
                rcAdapter.notifyDataSetChanged();
                dao.saveQuestion(q);
            } else if (requestCode == REQC_EDIT) {
                log("REQC EDIT");
                int index = data.getIntExtra("index", -1);
                questions.set(index, q);
                rcAdapter.notifyDataSetChanged();
                dao.saveQuestion(q);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
