package com.example.wishlist;

public class Gift {
    public String userID, giftID, name, comment, link, status, bookerID;

    Gift(){

    }
    public Gift(String userID, String giftID, String name, String comment, String link, String status, String bookerID) {
        this.userID = userID;
        this.giftID = giftID;
        this.name = name;
        this.comment = comment;
        this.link = link;
        this.status = status;
        this.bookerID = bookerID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGiftID() {
        return giftID;
    }

    public void setGiftID(String giftID) {
        this.giftID = giftID;
    }

    public String getBookerID() {
        return bookerID;
    }

    public void setBookerID(String bookerID) {
        this.bookerID = bookerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
