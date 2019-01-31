package com.geeksdobyte.sallysv2;

import java.io.Serializable;

public class sallysModel implements Serializable{

    public String timeStamp;
    public String barCodev;


    public String getBarCodev() {
        return barCodev;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setBarCodev(String barCodev) {
        this.barCodev = barCodev;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

