package com.example.wishlist;

public class User {
    public String userID, name, nickname, photo;

    User(){

    }
    public User(String userID, String name, String nickname, String photo) {
        this.userID = userID;
        this.name = name;
        this.nickname = nickname;
        this.photo = photo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
