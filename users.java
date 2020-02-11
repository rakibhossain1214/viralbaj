package com.example.viralbaj;

public class users {

    String fbid, mobileno, email, gender, birthday, location, agerange, imageurl, balance, uid, name , latittude, longitude;

    public users() {
    }

    public users(String fbid, String mobileno, String email, String gender, String birthday, String location, String agerange, String imageurl, String balance, String uid, String name, String latittude, String longitude) {
        this.fbid = fbid;
        this.mobileno = mobileno;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.location = location;
        this.agerange = agerange;
        this.imageurl = imageurl;
        this.balance = balance;
        this.uid = uid;
        this.name = name;
        this.latittude = latittude;
        this.longitude = longitude;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAgerange() {
        return agerange;
    }

    public void setAgerange(String agerange) {
        this.agerange = agerange;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatittude() {
        return latittude;
    }

    public void setLatittude(String latittude) {
        this.latittude = latittude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
