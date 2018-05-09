package com.example.admin.myapplication.Utils;

/**
 * Created by admin on 18.02.2018.
 */

public class PostValue {
    private String id,time,heading,link, sT;


    public PostValue(String id,String time, String heading, String link, String sT){
        this.time = time;
        this.heading = heading;
        this.link = "http://www.justmedia.ru" + link;
        this.id = id;
        this.sT = sT;


}

    public String getsT() {
        return sT;
    }

    public void setsT(String sT) {
        this.sT = sT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
