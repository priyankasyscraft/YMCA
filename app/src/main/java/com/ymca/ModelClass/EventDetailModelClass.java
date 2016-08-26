package com.ymca.ModelClass;

/**
 * Created by Soni on 26-Aug-16.
 */
public class EventDetailModelClass {

    private String description;
    private double lati;
    private double longi;
    private String locationAddress;
    private String dateTime;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getDateTime() {
        return dateTime;
    }

        public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
