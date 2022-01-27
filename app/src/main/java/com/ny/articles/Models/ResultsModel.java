package com.ny.articles.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsModel {

    @SerializedName("status")
    private String apiStatus;

    @SerializedName("results")
    private List<ArticlesModel> mostPopularArticles;

    public List<ArticlesModel> getMostPopularArticles() {
        return mostPopularArticles;
    }

    public String getStatus() {
        return apiStatus;
    }
}
