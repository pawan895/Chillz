package com.example.chillz;

import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("id")
    private Integer movieId;

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private String rating;

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getRating() {
        return rating;
    }
}
