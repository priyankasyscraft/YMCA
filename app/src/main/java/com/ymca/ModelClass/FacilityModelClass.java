package com.ymca.ModelClass;

/**
 * Created by Soni on 04-Aug-16.
 */
public class FacilityModelClass {

    private String facilityName;
    private String facilityAddress;
    private String facilityOpenClose;
    private boolean facilityStatus;

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

    public String getFacilityOpenClose() {
        return facilityOpenClose;
    }

    public void setFacilityOpenClose(String facilityOpenClose) {
        this.facilityOpenClose = facilityOpenClose;
    }

    public boolean isFacilityStatus() {
        return facilityStatus;
    }

    public void setFacilityStatus(boolean facilityStatus) {
        this.facilityStatus = facilityStatus;
    }
}
