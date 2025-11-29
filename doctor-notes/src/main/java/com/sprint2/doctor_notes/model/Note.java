package com.sprint2.doctor_notes.model;

import java.util.Arrays;

public class Note {

    private String id;

    private Long patientId;

    private String content;

    private String date;


    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }


    public Long getPatientId() 
    {
        return patientId;
    }

    public void setPatientId(Long patientId) 
    {
        this.patientId = patientId;
    }

    public String getContent() 
    {
        return content;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }


    public String getDate() 
    {
        return date;
    }

    public void setDate(String date) 
    {
        this.date = date;
    }
}
