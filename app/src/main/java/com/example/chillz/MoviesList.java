package com.example.chillz;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MoviesList {

    private Integer page;

    @SerializedName("results")
    private Results[] results;

    //getters and setters

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }
}
