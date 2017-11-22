package com.oab.lequiz.webservice;

import android.content.Context;
import android.widget.Toast;

import com.oab.lequiz.R;
import com.oab.lequiz.model.Quizz;
import com.oab.lequiz.util.Constants;
import com.oab.lequiz.util.DataManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrateur on 22/11/2017.
 */

public class QuizzDataManager {

    private static final String TAG = DataManager.class.getName();
    private static final QuizzDataManager ourInstance = new QuizzDataManager();

    private Quizz simpleQuestion;
    private Quizz normalQuestion;
    private Quizz expertQuestion;

    private Context appContext;
    private int nbRequest = 0;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.WS_URL)
            .build();

    QuizzClient client = null; //retrofit.create(QuizzClient.class);

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private ILoadQuestionListener listener;

    public static QuizzDataManager getInstance () {
        return ourInstance;
    }
    public void initDatabase (Context context, ILoadQuestionListener listener) {
        appContext = context.getApplicationContext();
        this.listener = listener;
        loadAsynchTask();

    }
    private void loadAsynchTask () {

        client.getSimpleQuestion().enqueue(new Callback<Quizz>() {
            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                simpleQuestion = response.body();
                checkProgress();
            }

            @Override
            public void onFailure(Call<Quizz> call, Throwable t) {
                Toast.makeText(appContext,"Erreur de connexion",Toast.LENGTH_LONG).show();
            }
        });

        client.getNormalQuestion().enqueue(new Callback<Quizz>() {
            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                normalQuestion = response.body();
                checkProgress();
            }

            @Override
            public void onFailure(Call<Quizz> call, Throwable t) {
                Toast.makeText(appContext,"Erreur de connexion",Toast.LENGTH_LONG).show();
            }
        });

        client.getExpertQuestion().enqueue(new Callback<Quizz>() {
            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                expertQuestion = response.body();
                checkProgress();
            }

            @Override
            public void onFailure(Call<Quizz> call, Throwable t) {
                Toast.makeText(appContext,"Erreur de connexion",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void checkProgress() {
        nbRequest ++;
        if (listener != null) {
            listener.onProgressChanged(nbRequest);
        }
    }

    private QuizzDataManager(){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constants.WS_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        client = retrofit.create(QuizzClient.class);
    }

    public Quizz getSimpleQuestion() {
        return simpleQuestion;
    }

    public Quizz getNormalQuestion() {
        return normalQuestion;
    }

    public Quizz getExpertQuestion() {
        return expertQuestion;
    }
}
