package com.oab.lequiz.database;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.oab.lequiz.model.Level;

import java.util.Date;

/**
 * Created by Administrateur on 23/11/2017.
 */

public class Converters {

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public Level fromInt(int value) {
        return Level.values()[value];
    }

    @TypeConverter
    public int LevelToInt(Level lev) {
        return lev.ordinal();
    }


}



