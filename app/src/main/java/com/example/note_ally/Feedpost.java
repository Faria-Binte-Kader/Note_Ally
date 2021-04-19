package com.example.note_ally;

public class Feedpost {
    private String tag;
    private String username, userId, postID;
    private String Details;
    private String like,dislike,report;

    public Feedpost() {
    }

    public Feedpost(String tg, String det, String usernam, String userid, String lik, String dislik, String rep, String pid) {

        tag = tg;
        Details = det;
        username = usernam;
        userId = userid;
        like = lik;
        dislike= dislik;
        report= rep;
        postID= pid;
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
    public String getLike() {
        return like;
    }
    public String getDisike() {
        return dislike;
    }
    public String getReport() {
        return report;
    }
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
    public void setlike(String lik) {
        like= lik;
    }
    public void setdislike(String dislik) {
        dislike= dislik;
    }
    public void setReport(String rep) {
        report= rep;
    }

    public void setdetails(String details) {
        Details = details;
    }

    public void settag(String tg) {
        tag = tg;
    }

}
