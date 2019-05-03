package com.example.richa.buttonclickapp.Object;

public class UserAccount {

    private String email;
    private String licensePlate;
    private String firstname;
    private String lastname;

    public UserAccount() {
    }

    public UserAccount(String email, String licensePlate, String firstname, String lastname) {
        this.email = email;
        this.licensePlate = licensePlate;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}