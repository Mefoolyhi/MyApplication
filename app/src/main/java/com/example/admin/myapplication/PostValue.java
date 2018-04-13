package com.example.admin.myapplication;

/**
 * Created by admin on 18.02.2018.
 */

public class PostValue {
    private String time,heading,link,more;


    public PostValue(String time, String heading, String link, String more){
        this.time = time;
        this.heading = heading;
        this.link = link;
        this.more = more;
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

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
