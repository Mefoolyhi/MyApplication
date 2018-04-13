package com.example.admin.myapplication;



public class Event {


    String title;
    long id;

    Event(String name,long id){
        this.id = id;
        this.title = name;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
