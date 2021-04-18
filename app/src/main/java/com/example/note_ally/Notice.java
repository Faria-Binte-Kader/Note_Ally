package com.example.note_ally;

public class Notice {

    private String Institution;
    private String Details;
    private String Tag;

    public Notice() {
    }

    public Notice(String ins, String det, String tag) {
        Institution = ins;
        Details = det;
        Tag = tag;
    }

    public String getInstitution() {
        return Institution;
    }

    public void setInstitution(String institution) {
        Institution = institution;
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