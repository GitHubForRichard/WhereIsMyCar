package com.example.richa.buttonclickapp.Object;

import java.io.Serializable;

public class LicensePlateInfo implements Serializable {

    private String photoUrl;
    private String licensePlateText;
    private int floor;
    private String location;

    public LicensePlateInfo() {}

    public LicensePlateInfo(String photoUrl, String licensePlateText, int floor, String location) {
        this.photoUrl = photoUrl;
        this.licensePlateText = licensePlateText;
        this.floor = floor;
        this.location = location;
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

}
