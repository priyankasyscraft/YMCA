package com.ymca.ModelClass;

import java.util.ArrayList;

/**
 * Created by Soni on 04-Aug-16.
 */
public class FacilityModelClass {


    private String facilityId;
    private String facilityName;
    private String facilityMiles;
    private String facilityAddress;
    private String facilityOpenCloseTime;
    private String facilityOpenCloseStatus;
    private ArrayList<String> facilityWeekDays = new ArrayList<>();

    public FacilityModelClass() {
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityAddress() {
        return facilityAddress;
    }

    public void setFacilityAddress(String facilityAddress) {
        this.facilityAddress = facilityAddress;
    }


    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityMiles() {
        return facilityMiles;
    }

    public void setFacilityMiles(String facilityMiles) {
        this.facilityMiles = facilityMiles;
    }

    public String getFacilityOpenCloseTime() {
        return facilityOpenCloseTime;
    }

    public void setFacilityOpenCloseTime(String facilityOpenCloseTime) {
        this.facilityOpenCloseTime = facilityOpenCloseTime;
    }

    public String getFacilityOpenCloseStatus() {
        return facilityOpenCloseStatus;
    }

    public void setFacilityOpenCloseStatus(String facilityOpenCloseStatus) {
        this.facilityOpenCloseStatus = facilityOpenCloseStatus;
    }

    public ArrayList<String> getFacilityWeekDays() {
        return facilityWeekDays;
    }

    public void setFacilityWeekDays(ArrayList<String> facilityWeekDays) {
        this.facilityWeekDays = facilityWeekDays;
    }

    public void addFacilityWeekDays(String facilityWeekDays) {
        this.facilityWeekDays.add(facilityWeekDays);
    }

    public void clearFacilityWeekDays() {
        this.facilityWeekDays.clear();
    }
}
