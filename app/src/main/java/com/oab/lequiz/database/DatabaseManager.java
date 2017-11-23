package com.oab.lequiz.database;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.oab.lequiz.model.GameResult;

import java.util.ArrayList;

/**
 * Created by Administrateur on 23/11/2017.
 */

public class DatabaseManager {
    private static DatabaseManager ourInstance = null;
    private QuizzDatabase database;
    private Context context;

    public static DatabaseManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DatabaseManager(context);
        }
        return ourInstance;
    }

    private DatabaseManager(Context context) {
        this.context = context;
        this.database = Room.databaseBuilder(context, QuizzDatabase.class, QuizzDatabase.DB_NAME).build();
    }

    public void storeGameResult(final GameResult result){
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.getGameResultDao().insertResult(result);
            }
        }).start();
    }

    @SuppressLint("StaticFieldLeak")
    public void getGameResults(final IGameResultListener listener){
        new AsyncTask<Void, Void,ArrayList<GameResult>>() {
            @Override
            protected ArrayList<GameResult> doInBackground(Void... voids) {
                return new ArrayList<GameResult>(database.getGameResultDao().getAllResults());
            }

            @Override
            protected void onPostExecute(ArrayList<GameResult> gameResults) {
//                super.onPostExecute(gameResults);
                if(listener != null) {
                    listener.onGameResultsRetreived(gameResults);
                }
            }
        }.execute();

    }
    public interface IGameResultListener{
        void onGameResultsRetreived (ArrayList<GameResult> results);
    }
}
