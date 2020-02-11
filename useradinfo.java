package com.example.viralbaj;

public class useradinfo {
    String name, profileImage, location, agerange, gender, uid;

    public useradinfo() {
    }

    public useradinfo(String name, String profileImage, String location, String agerange, String gender, String uid) {
        this.name = name;
        this.profileImage = profileImage;
        this.location = location;
        this.agerange = agerange;
        this.gender = gender;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
