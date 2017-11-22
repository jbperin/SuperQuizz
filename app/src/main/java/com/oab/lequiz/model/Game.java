package com.oab.lequiz.model;

import android.os.CountDownTimer;

import com.oab.lequiz.util.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrateur on 20/11/2017.
 */

public class Game {

    private static Game instance;

    private Integer score=0;

    private List<Question> questions = new ArrayList<>();

    private Level difficulty;

    private Integer index;

    private long duration;

    private Date startQuestionTime;

    private boolean isRunning = false;

    private CountDownTimer timer = new CountDownTimer(Constants.QUESTION_DURATION_MS, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (listener != null) {
                listener.onTick((int)(millisUntilFinished/1000));
            }
        }

        @Override
        public void onFinish() {
            if (listener != null) {
                listener.onTick(0);
            }
            onAnswer(null);
        }
    };

    private IGameListener listener;

    public Integer getScore() {
        return score;
    }

    private Game(){}

    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    public void startQuizz (IGameListener listener){
        if (isRunning) return;
        //initGame();
        this.listener = listener;
        index = 0;
        score = 0;
        duration = 0;
        isRunning = true;
        startNextQuestion();

    }
    public void startNextQuestion () {
        index++;
        startQuestionTime = new Date();
        timer.start();
        if ((listener != null) && index< questions.size()){
            listener.onNewQuestion();
        }
    }

    public Question getCurrentQuestion () {
        return (index != -1 && index< questions.size())?questions.get(index) : null;
    }
    public void pauseGame () {
        timer.cancel();
        isRunning = false;
        onAnswer(null);
    }
    public void onAnswer (String answer){
        long answerQuestion;
        if (answer == null){
            answerQuestion = Constants.QUESTION_DURATION_MS;
        } else {
            answerQuestion = new Date().getTime() - startQuestionTime.getTime();
        }

        if (timer != null) {
            timer.cancel();
            isRunning = false;
        }
        duration += answerQuestion;
        if (answer != null) {
            Question currentQuestion = getCurrentQuestion();
            if (answer.equalsIgnoreCase(currentQuestion.getAnswer())){
                score += answerQuestion < Constants.FAST_ANSWER_BONUS_DELAY ? 2 : 1;
            }
        }
        if (index == questions.size() - 1 && listener!=null) {
            timer.cancel();
            listener.onGameEnded();
        }
        if (listener != null) {

            listener.onQuestionEnded();
        }
    }

    public void setQuestions(Quizz questions) {
        this.questions = questions.getQuestions();
    }

    public interface IGameListener {
        public void onNewQuestion ();
        public void onQuestionEnded();
        public void onGameEnded();
        public void onTick(long dur);
    }

    public boolean isRunning() {
        return isRunning;
    }
}
