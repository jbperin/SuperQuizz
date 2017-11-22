package com.oab.lequiz.webservice;

import com.oab.lequiz.model.Quizz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrateur on 22/11/2017.
 */

public interface QuizzClient {
    @GET("/questions_simple")
    Call<Quizz> getSimpleQuestion();

    @GET("/questions_normal")
    Call<Quizz> getNormalQuestion();

    @GET("/questions_expert")
    Call<Quizz> getExpertQuestion();

}
