package com.sprint2.doctor_notes.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class NoteDTO {

    @NotBlank(message = "content ne peut pas être vide")
    @Size(min = 2, max = 1000)
    private String content;

    @NotNull(message = "patientId ne peut pas être null")
    private Long patientId;

    public String getContent() 
    {
        return content;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public Long getPatientId() 
    { 
        return patientId; 
    }
    public void setPatientId(Long patientId) 
    { 
        this.patientId = patientId; 
    }
}
