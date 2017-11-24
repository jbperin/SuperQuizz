package com.oab.lequiz.fragment;

import android.content.Context;

/**
 * Created by Administrateur on 24/11/2017.
 */

public class PreferenceManager {
    private static final String PREFERENCE_FILE = "prefs";
    private static final String HIGH_SCORE_KEY = "highScore";

    public static int getHighScore(Context context) {
        return context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).getInt(HIGH_SCORE_KEY, 0);

    }

    public static void updateHighScore(Context context, int score) {
        int lastScore = getHighScore(context);
        if (score > lastScore) {
            context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
                    .edit()
                    .putInt(HIGH_SCORE_KEY, score)
                    .apply();

        }

    }
}
