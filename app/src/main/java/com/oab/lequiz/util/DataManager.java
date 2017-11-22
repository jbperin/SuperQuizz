package com.oab.lequiz.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.oab.lequiz.R;
import com.oab.lequiz.model.BaseQuestions;
import com.oab.lequiz.model.Quizz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Administrateur on 21/11/2017.
 */

public class DataManager {
    private static final String TAG = DataManager.class.getName();
    private static final DataManager ourInstance = new DataManager();
    private Quizz simpleQuestion, normalQuestion, expertQuestion;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }
    public void initDatabase (Context context) {
        simpleQuestion = loadQuestion(context, R.raw.quizz_simple);
        normalQuestion = loadQuestion(context, R.raw.quizz_normal);
        expertQuestion = loadQuestion(context, R.raw.quizz_expert);
    }
    private Quizz loadQuestion(Context context, int quizzRes) {

        InputStream is = context.getResources().openRawResource(quizzRes);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        Quizz bddQuestions = new Gson().fromJson(jsonString, Quizz.class);
        return (bddQuestions);
    }


    public Quizz getSimpleQuestion() {
        return simpleQuestion;
    }

    public void setSimpleQuestion(Quizz simpleQuestion) {
        this.simpleQuestion = simpleQuestion;
    }

    public Quizz getNormalQuestion() {
        return normalQuestion;
    }

    public void setNormalQuestion(Quizz normalQuestion) {
        this.normalQuestion = normalQuestion;
    }

    public Quizz getExpertQuestion() {
        return expertQuestion;
    }

    public void setExpertQuestion(Quizz expertQuestion) {
        this.expertQuestion = expertQuestion;
    }
    private static final int PROGRESS_KEY = 42;



    public void launchDownload(final Context ctx, final Handler handler) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Faire le téléchargement
                //initDatabase();
                simpleQuestion =loadQuestion(ctx.getApplicationContext(), R.raw.quizz_simple);
                try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                Message progress;
                progress = handler.obtainMessage(PROGRESS_KEY, 1);
                progress.sendToTarget();
                normalQuestion = loadQuestion(ctx.getApplicationContext(), R.raw.quizz_normal);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progress = handler.obtainMessage(PROGRESS_KEY, 2);
                progress.sendToTarget();
                expertQuestion = loadQuestion(ctx.getApplicationContext(), R.raw.quizz_expert);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progress = handler.obtainMessage(PROGRESS_KEY, 3);
                progress.sendToTarget();

            }
        });
        thread.start();


    }
}
