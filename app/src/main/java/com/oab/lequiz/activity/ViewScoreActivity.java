package com.oab.lequiz.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.oab.lequiz.R;
import com.oab.lequiz.adapter.GameResultAdapter;
import com.oab.lequiz.database.DatabaseManager;
import com.oab.lequiz.model.GameResult;

import java.util.ArrayList;

public class ViewScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        DatabaseManager.getInstance(this).getGameResults(new DatabaseManager.IGameResultListener() {
            @Override
            public void onGameResultsRetreived(ArrayList<GameResult> results) {
                GameResultAdapter adapter = new GameResultAdapter(results);
                recyclerView.setAdapter(adapter);
            }
        });
    }

}
