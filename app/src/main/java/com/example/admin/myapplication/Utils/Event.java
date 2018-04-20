package com.example.admin.myapplication.Utils;



public class Event {


    String title,id,rating,imagelink,price,dates,place,url;

    public Event(String name, String id, String rating, String price, String imagelink, String dates, String place, String url){
        this.id = id;
        this.title = name;
        this.price = price;
        this.rating = rating;
        this.imagelink = imagelink;
        this.dates = dates;
        this.place = place;
        this.url = url;
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
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return id+" "+title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
