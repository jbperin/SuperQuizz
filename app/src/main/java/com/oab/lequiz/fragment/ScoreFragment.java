package com.oab.lequiz.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oab.lequiz.R;

public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    public ScoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance() {
        ScoreFragment fragment = new ScoreFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score, container, false);
        TextView tvBestScore = v.findViewById(R.id.bestScoreDisplay);
        Activity parentAct = getActivity();
        int highScore = 0;
        if (parentAct != null) {
            highScore = PreferenceManager.getHighScore(parentAct.getApplicationContext());
        }
        tvBestScore.setText("BestScore: " + String.valueOf(highScore));
        return v;


    }


}
