package com.ny.articles.Retrofit;

import com.ny.articles.Models.ResultsModel;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ResultsAPI {
    String BASE_URL = "https://api.nytimes.com/";
    String API_KEY = "9odT4RUGdhdA4cx0glkeR2KN0XEAqG3S";

    @POST("svc/mostpopular/v2/viewed/7.json?api-key=" + API_KEY)
    Call<ResultsModel> getPopularArticles();
}
