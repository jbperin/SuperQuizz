package com.oab.lequiz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oab.lequiz.R;
import com.oab.lequiz.model.GameResult;

import java.util.ArrayList;

/**
 * Created by Administrateur on 23/11/2017.
 */

public class GameResultAdapter extends RecyclerView.Adapter<GameResultAdapter.GameResultViewHolder> {

    private ArrayList<GameResult> items;

    public GameResultAdapter(ArrayList<GameResult> results) {
        super();
        this.items = results;
    }

    @Override
    public GameResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_result, parent ,false);
        return new GameResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameResultViewHolder holder, int position) {
        GameResult gameResult = items.get(position);
        holder.pseudo.setText(gameResult.getPseudo());
        holder.score.setText(String.valueOf(gameResult.getScore()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GameResultViewHolder  extends RecyclerView.ViewHolder{
        TextView pseudo;
        TextView score;
        public GameResultViewHolder(View itemView) {
            super(itemView);
            pseudo = itemView.findViewById(R.id.pseudo);
            score = itemView.findViewById(R.id.score);
        }
    }
}
