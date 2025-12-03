package com.openclassroom.diabetes_risk.model;

import lombok.Data;

@Data
public class Note {
    private int patientId;
    private String content;

    public Note() {}

    public Note(int patientId, String content) {
        this.patientId = patientId;
        this.content = content;
    }
    
    public int getPatientId() { 
        return patientId; 
    }
    public void setPatientId(int patientId) { 
        this.patientId = patientId; 
    }

    public String getContent() { 
        return content; 
    }
    public void setContent(String content) { 
        this.content = content; 
    }
}