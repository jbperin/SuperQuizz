package com.oab.lequiz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.oab.lequiz.R;
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
        final Button btnDemarrer = findViewById(R.id.btnDemarrer);
        btnDemarrer.setEnabled(false);
        final Button btnVoirScore = findViewById(R.id.btnViewScore);

        LinearLayout llLog = findViewById(R.id.layoutLog);
        llLog.setVisibility(View.GONE);

        EditText etPseudo = findViewById(R.id.editPseudo);
        etPseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) btnDemarrer.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDemarrer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                RadioButton rdSimple = findViewById(R.id.radioSimple);
                RadioButton rdNormal = findViewById(R.id.radioNormal);
                RadioButton rdExpert = findViewById(R.id.radioExpert);
                String strLevel = "simple";
                if (rdSimple.isChecked()) {
                    strLevel = "simple";
                } else if (rdNormal.isChecked()) {
                    strLevel = "normal";
                } else if (rdExpert.isChecked()) {
                    strLevel = "expert";
                }
                intent.putExtra("level", strLevel);
                intent.putExtra("pseudo", ((EditText )findViewById(R.id.editPseudo)).getText().toString());
                startActivity(intent);
                finish();
            }
        });
        btnVoirScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), ViewScoreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onProgressChanged(int nbreq) {
        Log.d(TAG, "on en est à " + nbreq);
        if (nbreq == 3) {


            LinearLayout llLog = findViewById(R.id.layoutLog);
            llLog.setVisibility(View.VISIBLE);

            ProgressBar progBar = findViewById(R.id.progressBar2);
            progBar.setVisibility(View.GONE);

        }
    }





}
