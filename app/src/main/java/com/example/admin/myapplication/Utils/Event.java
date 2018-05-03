package com.example.admin.myapplication.Utils;


import java.util.ArrayList;

public class Event {

    public String getToBuy() {
        return toBuy;
    }

    public void setToBuy(String toBuy) {
        this.toBuy = toBuy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceImgUrl() {
        return placeImgUrl;
    }

    public void setPlaceImgUrl(String placeImgUrl) {
        this.placeImgUrl = placeImgUrl;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    String title,rating,imagelink,price,dates,place, toBuy, address, placeImgUrl;
    String types;
    Double userRating,longitude,latitude;

    public Event(Double longitude, Double latitude, Double userRating, String name, String rating, String price, String imagelink, String dates,
                 String place, String toBuy, String address, String placeImgUrl, String types){

        this.latitude = latitude;
        this.longitude = longitude;
        this.userRating = userRating;

        this.title = name;
        this.price = price;
        this.rating = rating;
        this.imagelink = imagelink;
        this.dates = dates;
        this.place = place;
        this.toBuy = toBuy;
        this.address = address;
        this.types = types;
        this.placeImgUrl = placeImgUrl;


    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getPrice() {
        if (price == "null")
            return "";
        else
            return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDates() {
        if (dates == "null")
            return "";
        else
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getPlace() {
        if (place == "null")
            return "";
        else
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        if (title == "null")
            return "";
        else
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
