package com.ymca.ModelClass;

import java.util.ArrayList;

/**
 * Created by Soni on 30-Jul-16.
 */
public class EventModelClass {

    private String eventId;
    private String eventName;
    private String eventStartDates;
    private String eventEndDates;
    private String eventStratEndTime;
    private ArrayList<String> startDates = new ArrayList<>();


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventEndDates() {
        return eventEndDates;
    }

    public void setEventEndDates(String eventEndDates) {
        this.eventEndDates = eventEndDates;
    }

    public String getEventStartDates() {
        return eventStartDates;
    }

    public void setEventStartDates(String eventStartDates) {
        this.eventStartDates = eventStartDates;
    }

    public ArrayList<String> getStartDates() {
        return startDates;
    }

    public void setStartDates(ArrayList<String> startDates) {
        this.startDates = startDates;
    }

    public void addStartDates(String startDates) {
        this.startDates.add(startDates);
    }

    public void clearStartDates() {
        this.startDates.clear();
    }

    public String getEventStratEndTime() {
        return eventStratEndTime;
    }

    public void setEventStratEndTime(String eventStratEndTime) {
        this.eventStratEndTime = eventStratEndTime;
    }
}
