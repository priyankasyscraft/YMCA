package com.ymca.ModelClass;

/**
 * Created by Soni on 03-Aug-16.
 */
public class CampModelClass {

    private String campName;
    private String campAddress;
    private String campCounter;

    public CampModelClass(){}


    public String getCampAddress() {
        return campAddress;
    }

    public void setCampAddress(String campAddress) {
        this.campAddress = campAddress;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampCounter() {
        return campCounter;
    }

    public void setCampCounter(String campCounter) {
        this.campCounter = campCounter;
    }
}
