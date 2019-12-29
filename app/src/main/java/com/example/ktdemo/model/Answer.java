package com.example.ktdemo.model;

import java.io.Serializable;

public class Answer implements Serializable {

    int id;
    int rate;
    String date;
    int questionId;

    public Answer() {
        this.id=-1;
    }

    public Answer(int rate, String date, int questionId) {
        this.id=-1;
        this.rate = rate;
        this.date = date;
        this.questionId = questionId;
    }

    public Answer(int id, int rate, String date, int questionId) {
        this.id = id;
        this.rate = rate;
        this.date = date;
        this.questionId = questionId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
