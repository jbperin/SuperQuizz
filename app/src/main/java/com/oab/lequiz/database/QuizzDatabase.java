package com.oab.lequiz.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.oab.lequiz.model.GameResult;

/**
 * Created by Administrateur on 23/11/2017.
 */
@Database(entities = {GameResult.class}, version = 1)
public abstract class QuizzDatabase extends RoomDatabase{

    public static final String DB_NAME = "quizz.db";

    public abstract GameResultDAO getGameResultDao();
}
