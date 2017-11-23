package com.oab.lequiz.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.oab.lequiz.model.GameResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrateur on 23/11/2017.
 */

@Dao
public interface GameResultDAO {
    @Insert
    void insertResult(GameResult gameResult);

    @Query("SELECT * from game_result")
    List<GameResult> getAllResults ();
}
