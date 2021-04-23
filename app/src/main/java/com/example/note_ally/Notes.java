package com.example.note_ally;

public class Notes {
    private String Title;
    private String Details;
    private String postID;

    public Notes() {
    }

    public Notes(String ttl, String det, String pid) {
        Title = ttl;
        Details = det;
        postID = pid;
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String pid) {
        postID = pid;
    }
}
