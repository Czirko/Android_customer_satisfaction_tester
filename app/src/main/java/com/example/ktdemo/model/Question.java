package com.example.ktdemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    private int id;
    private String quest;
    private int weight;
    private boolean active;
    public List<Answer> answers;

    public Question() {

        this.id=-1;
    }

    public Question(String quest, int weight, boolean active, List<Answer> answers) {
        this.quest = quest;
        this.weight = weight;
        this.active = active;
        this.answers = answers;
        this.id=-1;
    }

    public Question(int id, String quest, int weight, boolean active) {
        this.id = id;
        this.quest = quest;
        this.weight = weight;
        this.active = active;
        this.answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
