package com.example.note_ally;

public class Event {

    private String Organizer;
    private String Location;
    private String Details;
    private String Tag;

    public Event() {
    }

    public Event(String org, String loc, String det, String tag) {
        Organizer = org;
        Location = loc;
        Details = det;
        Tag = tag;
    }

    public String getOrganizer() {
        return Organizer;
    }

    public void setOrganizer(String organizer) {
        Organizer = organizer;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
