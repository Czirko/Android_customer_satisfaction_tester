package com.example.ktdemo.dao;

import com.example.ktdemo.model.Answer;

import java.util.List;

public interface AnswerDao {

    public List<Answer> getAllAnswer();
    public void deleteAnswer(Answer a);
    public void saveAnswer(Answer a);
    public void deleteAllAnswer();
}
