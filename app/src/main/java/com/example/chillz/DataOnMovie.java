package com.example.chillz;

import com.google.gson.annotations.SerializedName;

public class DataOnMovie {

    private String original_title,overview,backdrop_path,release_date,homepage;
    private Integer budget,revenue,runtime;
    private float vote_average;

    @SerializedName("genres")
    private Genre[] gernes;

    @SerializedName("production_companies")
    private ProductionCompany[] productionCompanies;

    @SerializedName("spoken_languages")
    private SpokenLanguage[] spokenLanguage;

    //Setters
    public void setSpokenLanguage(SpokenLanguage[] spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }
    public void setProductionCompanies(ProductionCompany[] productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
    public void setGernes(Genre[] gernes) {
        this.gernes = gernes;
    }

    //Getters
   public SpokenLanguage[] getSpokenLanguage() {
        return spokenLanguage;
    }

    public ProductionCompany[] getProductionCompanies() {
        return productionCompanies;
    }

    public Genre[] getGernes() {
        return gernes;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public Integer getBudget() {
        return budget;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public float getVote_average() {
        return vote_average;
    }

    public Integer getRuntime() {
        return runtime;
    }
}
