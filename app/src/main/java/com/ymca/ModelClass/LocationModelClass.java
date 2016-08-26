package com.ymca.ModelClass;

/**
 * Created by Soni on 28-Jul-16.
 */
public class LocationModelClass {

    public LocationModelClass(){}

    private String locationId;
    private String locationName;
    private String locationMiles;
    private String locationAddress;
    private String locationOpenCloseTime;
    private String locationOpenCloseStatus;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationMiles() {
        return locationMiles;
    }

    public void setLocationMiles(String locationMiles) {
        this.locationMiles = locationMiles;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationOpenCloseStatus() {
        return locationOpenCloseStatus;
    }

    public void setLocationOpenCloseStatus(String locationOpenCloseStatus) {
        this.locationOpenCloseStatus = locationOpenCloseStatus;
    }

    public String getLocationOpenCloseTime() {
        return locationOpenCloseTime;
    }

    public void setLocationOpenCloseTime(String locationOpenCloseTime) {
        this.locationOpenCloseTime = locationOpenCloseTime;
    }
}
