package com.jnu.android_test_demo.data;

public class MyLocation {
    private String name, memo;
    private double  latitude, longitude;

    public MyLocation(String name, double latitude, double longitude, String memo) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.memo = memo;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getMemo() {
        return memo;
    }
}
