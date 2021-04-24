package com.example.note_ally;

public class Modelpdf {
    private String tag,pname;
    private String username, userId;
    private String Details;
    private String downloadlink;

    public Modelpdf() {
    }

    public Modelpdf(String tg, String det, String usernam, String userid, String link, String pnam) {

        tag = tg;
        Details = det;
        username = usernam;
        userId = userid;
        downloadlink = link;
        pname= pnam;

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
    public String getPname(){return pname;}



    public void setUsername(String nam) {
        username = nam;
    }
    public void setPname(String nam) {
        pname = nam;
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
}
