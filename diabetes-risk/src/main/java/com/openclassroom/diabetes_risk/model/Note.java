package com.openclassroom.diabetes_risk.model;

import lombok.Data;

@Data
public class Note {
    private String patientId;
    private String patient;
    private String content;

    public String getPatientId() { 
        return patientId; 
    }
    public void setPatientId(String patientId) { 
        this.patientId = patientId; 
    }
    public String getPatient() { 
        return patient; 
    }
    public void setPatient(String patient) { 
        this.patient = patient; 
    }
    public String getContent() { 
        return content; 
    }
    public void setContent(String content) { 
        this.content = content; 
    }
}