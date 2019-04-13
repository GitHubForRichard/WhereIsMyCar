package com.example.richa.buttonclickapp.Object;

public class SearchHistory {

    public String userId;
    public String searchedLicensePlate;

    public SearchHistory() {
    }

    public SearchHistory(String userId, String searchedLicensePlate) {
        this.userId = userId;
        this.searchedLicensePlate = searchedLicensePlate;
    }

    public String getUserId() {
        return userId;
    }
}
