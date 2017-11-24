package com.oab.lequiz;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.oab.lequiz.database.DatabaseManager;
import com.oab.lequiz.model.GameResult;
import com.oab.lequiz.model.Level;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void checkDatabase() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DatabaseManager dbManager = DatabaseManager.getInstance(appContext);
        final ArrayList<GameResult> games = new ArrayList<>();
        for (int ii = 0; ii < 15; ii++) {
            GameResult result = new GameResult();
            result.setDate(new Date());
            result.setPseudo("Pseudo" + ii);
            result.setScore(1000*ii);
            result.setLevel(Level.EXPERT);
            result.setDuration(12000);
            games.add(result);
            dbManager.storeGameResult(result);
        }
        Thread.sleep(1000);

        dbManager.getGameResults(new DatabaseManager.IGameResultListener() {
            @Override
            public void onGameResultsRetreived(ArrayList<GameResult> results) {
                for (GameResult res: results){
                    Log.d("TEST ", res.toString());
                    assertTrue(games.contains(res));
                }
            }
        });

        assertEquals("com.oab.lequiz", appContext.getPackageName());
    }
}
