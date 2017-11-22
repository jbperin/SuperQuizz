package com.oab.lequiz;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.oab.lequiz.activity.MainActivity;
import com.oab.lequiz.activity.ScoreActivity;
import com.oab.lequiz.util.DataManager;
import com.oab.lequiz.webservice.ILoadQuestionListener;
import com.oab.lequiz.webservice.QuizzDataManager;

public class SplashActivity extends AppCompatActivity implements ILoadQuestionListener{
    private static final String TAG = SplashActivity.class.getName();

    private static final int PROGRESS_KEY = 42;

    private Handler handler = new Handler(Looper.getMainLooper()) {

        int step;

        @Override
        public void handleMessage(Message msg) {
            step = (int)msg.obj;
            switch (msg.what){
                case PROGRESS_KEY :
                    Log.e(TAG, "Progress : "+step );
                    break;
            }
            super.handleMessage(msg);
            if (step == 3) {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);

                //intent.putExtra("score", currentGame.getScore());
                startActivity(intent);
                finish();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //DataManager.getInstance().launchDownload(getApplicationContext(), handler);
        QuizzDataManager.getInstance().initDatabase(getApplicationContext(), this);


    }

    @Override
    public void onProgressChanged(int nbreq) {
        Log.d(TAG, "on en est à " + nbreq);
        if (nbreq == 3) {
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);

            //intent.putExtra("score", currentGame.getScore());
            startActivity(intent);
            finish();
        }
    }
}
