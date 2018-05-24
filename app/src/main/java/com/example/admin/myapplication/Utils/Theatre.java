package com.example.admin.myapplication.Utils;

public class Theatre {
    String title, address,image, link, number,id;
    double latitude, longintude;

    Theatre(String id, String title, String address, String image, String link, String number, double latitude, double longintude){
        this.latitude = latitude;
        this.title = title;
        this.longintude = longintude;
        this.link = link;
        this.number = number;
        this.address = address;
        this.id = id;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongintude() {
        return longintude;
    }

    public void setLongintude(double longintude) {
        this.longintude = longintude;
    }
}
