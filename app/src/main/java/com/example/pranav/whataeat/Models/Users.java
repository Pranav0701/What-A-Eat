package com.example.pranav.whataeat.Models;

public class Users {
    private String FirstName,LastName,City,MobileNumber,Address,EmailId,FirstloginDatabase;

    public Users() {
    }

    public Users(String firstName, String lastName, String city, String mobileNumber, String address, String emailId,String firstloginDatabase) {
        FirstName = firstName;
        LastName = lastName;
        City = city;
        MobileNumber = mobileNumber;
        Address = address;
        EmailId = emailId;
        FirstloginDatabase=firstloginDatabase;
    }

    public Users(String firstloginDatabase) {
        FirstloginDatabase = firstloginDatabase;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getFirstloginDatabase() {
        return FirstloginDatabase;
    }

    public void setFirstloginDatabase(String firstloginDatabase) {
        FirstloginDatabase = firstloginDatabase;
    }
}
