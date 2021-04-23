package com.example.note_ally;

public class Job {

    private String Company;
    private String Position;
    private String Details;
    private String Tag;
    private String postID;

    public Job() {
    }

    public Job(String com, String pos, String det, String tag, String pid) {
        Company = com;
        Position = pos;
        Details = det;
        Tag = tag;
        postID = pid;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String pid) {
        postID = pid;
    }

}