package com.openclassroom.diabetes_risk.model;

public class RiskReport {
    private String patientId;
    private String fullName;
    private int age;
    private String genre;
    private String risk;

    public RiskReport(String patientId, String fullName, int age, String genre, String risk) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.age = age;
        this.genre = genre;
        this.risk = risk;
    }

    public String getPatientId() { 
        return patientId; 
    }
    public void setPatientId(String patientId) { 
        this.patientId = patientId; 
    }
    public String getFullName() { 
        return fullName; 
    }
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }
    public int getAge() { 
        return age; 
    }
    public void setAge(int age) { 
        this.age = age; 
    }
    public String getGenre() { 
        return genre; 
    }
    public void setGenre(String genre) { 
        this.genre = genre; 
    }
    public String getRisk() { 
        return risk; 
    }
    public void setRisk(String risk) { 
        this.risk = risk; 
    }
}
