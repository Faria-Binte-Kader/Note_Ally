package com.example.note_ally;

public class AdmissionHelp {

    private String Institutionsubject;
    private String Details;
    private String Tag;

    public AdmissionHelp() {
    }

    public AdmissionHelp(String ins, String det, String tag) {
        Institutionsubject = ins;
        Details = det;
        Tag = tag;
    }

    public String getInstitutionsubject() {
        return Institutionsubject;
    }

    public void setInstitutionsubject(String institutionsubject) {
        Institutionsubject = institutionsubject;
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