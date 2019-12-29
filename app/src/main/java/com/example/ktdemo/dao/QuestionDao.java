package com.example.ktdemo.dao;

import com.example.ktdemo.model.Question;

import java.util.List;

public interface QuestionDao {

    public List<Question> getAllQuestion();
    public void deleteQuestion(Question q);
    public void saveQuestion(Question q);
    public void deleteAllQuestion();
}
