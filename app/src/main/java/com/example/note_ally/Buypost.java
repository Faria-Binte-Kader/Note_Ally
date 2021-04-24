package com.example.note_ally;

public class Buypost {
    private String tag,productname;
    private String username, userId, postID;
    private String Details;
    private String downloadlink;

    public Buypost() {
    }

    public Buypost(String tg, String det, String usernam, String userid, String link,String pronam,String pid) {

        tag = tg;
        Details = det;
        username = usernam;
        userId = userid;
        downloadlink = link;
        productname = pronam;
        postID = pid;

    }

    public String gettag() {
        return tag;
    }
    public String getdetails() {
        return Details;
    }
    public String getUsername() {
        return username;
    }
    public String getUserId() {
        return userId;
    }
    public String getDownloadlink() {
        return downloadlink;
    }
    public String getProductname() { return productname;}
    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
    public void setUsername(String nam) {
        username = nam;
    }
    public void setuserid(String id) {
        userId = id;
    }
    public void setDownloadlink(String link) {
        downloadlink= link;
    }
    public void setdetails(String details) {
        Details = details;
    }
    public void settag(String tg) {
        tag = tg;
    }
    public void setProductname(String pn) {productname=pn;}
}
