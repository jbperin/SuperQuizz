package com.oab.lequiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrateur on 20/11/2017.
 */

public class Question {
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String question;
    private List<String> options = new ArrayList<>();
    private Level level;
    private String answer;
    private String anecdote;

    public String getAnecdote() {
        return anecdote;
    }

    public void setAnecdote(String anecdote) {
        this.anecdote = anecdote;
    }
}
