package com.oab.lequiz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oab.lequiz.R;
import com.oab.lequiz.model.Game;
import com.oab.lequiz.model.Level;
import com.oab.lequiz.model.Question;
import com.oab.lequiz.util.Constants;
import com.oab.lequiz.util.DataManager;
import com.oab.lequiz.webservice.QuizzDataManager;


import java.util.List;

public class MainActivity extends AppCompatActivity implements Game.IGameListener {

    private final String TAG = MainActivity.class.getName();

    private Game currentGame;

    TextView tvScore;
    TextView tvQuestion;
    Button btnSol1;
    Button btnSol2;
    Button btnSol3;
    Button btnSol4;
    Button btnNext;
    ProgressBar prgBar;

    String level;
    String pseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        Bundle extra = getIntent().getExtras();
        if (extra!= null) {
            level = extra.getString("level", "simple");
            pseudo = extra.getString("pseudo", "foo");
        }


        initViews();
        currentGame = Game.getInstance();
        //currentGame.initGame();
        //DataManager.getInstance().initDatabase(this);
        if (level.equalsIgnoreCase("simple")) {
            currentGame.setDifficulty(Level.BEGINNER);
            currentGame.setQuestions(QuizzDataManager.getInstance().getSimpleQuestion());
        }
        if (level.equalsIgnoreCase("normal")) {
            currentGame.setDifficulty(Level.INTERMEDIATE);
            currentGame.setQuestions(QuizzDataManager.getInstance().getNormalQuestion());
        }
        if (level.equalsIgnoreCase("expert")) {
            currentGame.setDifficulty(Level.EXPERT);
            currentGame.setQuestions(QuizzDataManager.getInstance().getExpertQuestion());
        }
        //if(!currentGame.isRunning()) {
        currentGame.setGamerPseudo(pseudo);
        currentGame.startQuizz(this);
        //}
    }

    private void initViews() {

        tvScore = findViewById(R.id.scoreView);
        tvQuestion = findViewById(R.id.questionView);
        btnSol1 = findViewById(R.id.btnSol1);
        btnSol2 = findViewById(R.id.btnSol2);
        btnSol3 = findViewById(R.id.btnSol3);
        btnSol4 = findViewById(R.id.btnSol4);
        btnNext = findViewById(R.id.btnNext);
        prgBar = findViewById(R.id.progressBar);
        prgBar.setMax(10);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentGame.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            int idBtn = btn.getId();
            switch (idBtn) {
                case R.id.btnSol1:
                    Log.d(TAG, "bouton 1 cliqué");
                    currentGame.onAnswer(currentGame.getCurrentQuestion().getOptions().get(0));
                    break;
                case R.id.btnSol2:
                    Log.d(TAG, "bouton 2 cliqué");
                    currentGame.onAnswer(currentGame.getCurrentQuestion().getOptions().get(1));
                    break;
                case R.id.btnSol3:
                    Log.d(TAG, "bouton 3 cliqué");
                    currentGame.onAnswer(currentGame.getCurrentQuestion().getOptions().get(2));
                    break;
                case R.id.btnSol4:
                    Log.d(TAG, "bouton 4 cliqué");
                    currentGame.onAnswer(currentGame.getCurrentQuestion().getOptions().get(3));
                    break;
                case R.id.btnNext:
                    Log.d(TAG, "nouvelle question demandée");
                    currentGame.onAnswer("False Answer");
                    break;
            }
        }
    };
    @Override
    public void onNewQuestion() {
        Log.d(TAG, "onNewQuestion");
        prgBar.setProgress(0);
        Question currentQuestion = currentGame.getCurrentQuestion();
        tvQuestion.setText(currentQuestion.getQuestion());
        tvScore.setText(String.valueOf(currentGame.getScore()));
        List<String> options = currentQuestion.getOptions();
        btnSol1.setText(options.get(0));
        btnSol2.setText(options.get(1));
        btnSol3.setText(options.get(2));
        btnSol4.setText(options.get(3));
        btnSol1.setOnClickListener(clickListener);
        btnSol2.setOnClickListener(clickListener);
        btnSol3.setOnClickListener(clickListener);
        btnSol4.setOnClickListener(clickListener);
        btnNext.setOnClickListener(clickListener);
    }

    @Override
    public void onQuestionEnded() {
        Log.d(TAG, "onQuestionEnded");
        currentGame.startNextQuestion();
    }

    @Override
    public void onGameEnded() {
        Log.d(TAG, "onGameEnded");

        Intent intent = new Intent (this, ScoreActivity.class);
        intent.putExtra("score", currentGame.getScore());
        intent.putExtra("pseudo", currentGame.getGamerPseudo());
        intent.putExtra("duration", currentGame.getDuration());


        startActivity(intent);

    }

    @Override
    public void onTick(long dur) {
        Log.d(TAG, "onTick : "+dur + " s");
        //int progress = prgBar.getProgress() + 1;
        prgBar.setProgress((int)(Constants.QUESTION_DURATION_MS/1000 - dur));
    }
}
