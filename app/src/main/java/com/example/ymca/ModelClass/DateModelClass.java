package com.example.ymca.ModelClass;

/**
 * Created by Soni on 28-Jul-16.
 */
public class DateModelClass {

    private String scheduleDateTime;
    private String scheduleDateName;
    private String scheduleDateNameWith;
    private String scheduleDateAppointmentTime;
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

    public String getScheduleDateAppointmentTime() {
        return scheduleDateAppointmentTime;
    }

    public void setScheduleDateAppointmentTime(String scheduleDateAppointmentTime) {
        this.scheduleDateAppointmentTime = scheduleDateAppointmentTime;
    }

    public String getScheduleDatePlace() {
        return scheduleDatePlace;
    }

    public void setScheduleDatePlace(String scheduleDatePlace) {
        this.scheduleDatePlace = scheduleDatePlace;
    }
}
