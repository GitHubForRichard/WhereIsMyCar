package com.example.richa.buttonclickapp.Object;

public class LicensePlateInfo {

    public String photoUrl;
    public String licensePlateText;

    public LicensePlateInfo() {}

    public LicensePlateInfo(String photoUrl, String licensePlateText) {
        this.photoUrl = photoUrl;
        this.licensePlateText = licensePlateText;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setLicensePlateText(String licensePlateText) {
        this.licensePlateText = licensePlateText;
    }
}