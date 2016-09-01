package com.ymca.ModelClass;

/**
 * Created by Soni on 28-Jul-16.
 */
public class DateModelClass {

    private String scheduleDateId;
    private String scheduleDateTime;
    private String scheduleDateName;
    private String scheduleDateNameWith;
    private String scheduleDateWeekDays;
    private String scheduleDatePlace;

    public DateModelClass(){}


    public String getScheduleDateTime() {
        return scheduleDateTime;
    }

    public void setScheduleDateTime(String scheduleDateTime) {
        this.scheduleDateTime = scheduleDateTime;
    }

    public String getScheduleDateName() {
        return scheduleDateName;
    }

    public void setScheduleDateName(String scheduleDateName) {
        this.scheduleDateName = scheduleDateName;
    }

    public String getScheduleDateNameWith() {
        return scheduleDateNameWith;
    }

    public void setScheduleDateNameWith(String scheduleDateNameWith) {
        this.scheduleDateNameWith = scheduleDateNameWith;
    }



    public String getScheduleDatePlace() {
        return scheduleDatePlace;
    }

    public void setScheduleDatePlace(String scheduleDatePlace) {
        this.scheduleDatePlace = scheduleDatePlace;
    }

    public String getScheduleDateId() {
        return scheduleDateId;
    }

    public void setScheduleDateId(String scheduleDateId) {
        this.scheduleDateId = scheduleDateId;
    }

    public String getScheduleDateWeekDays() {
        return scheduleDateWeekDays;
    }

    public void setScheduleDateWeekDays(String scheduleDateWeekDays) {
        this.scheduleDateWeekDays = scheduleDateWeekDays;
    }

    public boolean isValidSearch(String searchTerm) {
        if (getScheduleDateName().toLowerCase().contains(searchTerm.toLowerCase()))
            return true;
        return false;
    }/* public boolean isValidSearch(String searchTerm) {
        if (getAddress().toLowerCase().contains(searchTerm.toLowerCase())
                || getName().toLowerCase().contains(searchTerm.toLowerCase())
                || getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
            return true;
        return false;
    }*/

}
