package com.oab.lequiz.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.oab.lequiz.database.Converters;

import java.util.Date;

/**
 * Created by Administrateur on 23/11/2017.
 */
@Entity(tableName = "game_result")
public class GameResult {

    @PrimaryKey(autoGenerate = true)
    int id;

    String pseudo;
    int score;
    long duration;

    @TypeConverters({Converters.class})
    Level level;

    @TypeConverters({Converters.class})
    Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameResult result = (GameResult) o;

        if (score != result.score) return false;
        if (duration != result.duration) return false;
        if (!pseudo.equals(result.pseudo)) return false;
        if (level != result.level) return false;
        return date.equals(result.date);
    }

    @Override
    public int hashCode() {
        int result = pseudo.hashCode();
        result = 31 * result + score;
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + level.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
