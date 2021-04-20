package com.example.note_ally;

public class Notes {
    private String Title;
    private String Details;

    public Notes() {
    }

    public Notes(String ttl, String det) {
        Title = ttl;
        Details = det;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
