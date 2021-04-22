package com.example.note_ally;

public class Tags {
    private String tagname, userId, postID;

    public Tags() {
    }

    public Tags(String nam, String uid, String pid) {

        tagname = nam;
        userId = uid;
        postID = pid;
    }

    public String getTagname() {
        return tagname;
    }
    public String getUserId() {
        return userId;
    }
    public String getPostID() {
        return postID;
    }

    public void setTagname(String tag) {
        tagname = tag;
    }
    public void setPostID(String postID) {
        this.postID = postID;
    }
    public void setuserid(String id) {
        userId = id;
    }

}
