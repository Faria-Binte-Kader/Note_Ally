package com.example.note_ally;

public class JobNotification {
    private String njcompany, njposition, njdetails;

    public JobNotification() {
    }

    public JobNotification(String njc, String njp, String njd) {

        njcompany = njc;
        njposition = njp;
        njdetails = njd;
    }

    public String getNjcompany() {
        return njcompany;
    }
    public String getNjposition() {
        return njposition;
    }
    public String getNjdetails() {
        return njdetails;
    }

    public void setNjcompany(String njc) {
        njcompany = njc;
    }
    public void setNjposition(String njp) {
        njposition = njp;
    }
    public void setNjdetails(String njd) {
        njdetails = njd;
    }

}
