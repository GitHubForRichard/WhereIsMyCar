package com.example.richa.buttonclickapp.Object;

import java.io.Serializable;

public class LicensePlateInfo implements Serializable {

    private String photoUrl;
    private String licensePlateText;
    private int floor;
    private String location;
    private String garage;

    public LicensePlateInfo() {}

    public LicensePlateInfo(String photoUrl, String licensePlateText, int floor, String location, String garage) {
        this.photoUrl = photoUrl;
        this.licensePlateText = licensePlateText;
        this.floor = floor;
        this.location = location;
        this.garage = garage;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setLicensePlateText(String licensePlateText) {
        this.licensePlateText = licensePlateText;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getLicensePlateText() {
        return licensePlateText;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }
}