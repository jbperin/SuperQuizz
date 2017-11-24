package com.oab.lequiz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oab.lequiz.R;
import com.oab.lequiz.database.DatabaseManager;
import com.oab.lequiz.model.GameResult;
import com.oab.lequiz.model.Level;

import java.util.Date;

public class ScoreActivity extends AppCompatActivity {

    int score=0;
    String pseudo;
    long duration;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo AdapterContextMenuInfoinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.entShare:

                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.result_string,score));
                intent.setType("text/plain");
                if  (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Aucune application trouvée :(", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                super.onContextItemSelected(item);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean res = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return (res);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);

        Bundle extra = getIntent().getExtras();
        if (extra!= null) {
            score = extra.getInt("score", 0);
            pseudo = extra.getString("pseudo", "toto");
            duration = extra.getLong("duration", 0);
        }
        TextView tvScore = findViewById(R.id.scoreSentence);
        tvScore.setText(getString(R.string.result_string, score));

        GameResult gr = new GameResult();
        gr.setScore(score);
        gr.setDuration(duration);
        gr.setPseudo(pseudo);
        gr.setDate(new Date());
        gr.setLevel(Level.EXPERT);
        // ???gr.setLevel();
        DatabaseManager.getInstance(this).storeGameResult(gr);
        Button btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
