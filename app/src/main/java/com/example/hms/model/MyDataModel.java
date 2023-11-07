package com.example.hms.model;

public class MyDataModel {
    private String evidenceImage;
    private String complaint;
    private String resName;

    public MyDataModel(String evidenceImage, String complaint, String resName) {
        this.evidenceImage = evidenceImage;
        this.complaint = complaint;
        this.resName = resName;
    }

    public String getEvidenceImage() {
        return evidenceImage;
    }

    public void setEvidenceImage(String evidenceImage) {
        this.evidenceImage = evidenceImage;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
}

