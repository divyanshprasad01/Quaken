package com.example.quaken;

import java.util.ArrayList;

public class newEarthQuake {
    Double magnitude;
    String location;
    long time;

    public newEarthQuake(Double magnitude, String location, long time) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
    }



    public Double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getTime() {
        return time;
    }
}
