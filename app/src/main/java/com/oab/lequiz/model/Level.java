package com.oab.lequiz.model;

/**
 * Created by Administrateur on 20/11/2017.
 */

public enum Level {
    BEGINNER ("Débutant")
    , INTERMEDIATE ("intermediaire")
    , EXPERT ("Expert");

    String description;

    Level(String description) {
        this.description = description;
    }
}
