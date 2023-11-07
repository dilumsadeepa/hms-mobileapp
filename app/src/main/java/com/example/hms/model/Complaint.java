package com.example.hms.model;

public class Complaint {
    private int complaintId;
    private String complaint;
    private String evidenceImage;

    public Complaint(int complaintId, String complaint, String evidenceImage) {
        this.complaintId = complaintId;
        this.complaint = complaint;
        this.evidenceImage = evidenceImage;
    }

    // Getters and setters for the attributes
    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getEvidenceImage() {
        return evidenceImage;
    }

    public void setEvidenceImage(String evidenceImage) {
        this.evidenceImage = evidenceImage;
    }
}

