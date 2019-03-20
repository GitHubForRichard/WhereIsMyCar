package com.example.richa.buttonclickapp.Object;

public class UserInfo {

    public String email;
    public String licensePlate;
    public String brand;
    public String color;
    public int year;

    public UserInfo() {}

    public UserInfo(String email, String licensePlate, String brand, String color, int year) {
        this.email = email;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.color = color;
        this.year = year;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
