package com.sprint2.doctor_notes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NoteDTO {

    @NotNull(message = "patientId ne peut pas être null")
    private Integer patientId;

    @NotBlank(message = "content ne peut pas être vide")
    @Size(min = 2, max = 1000)
    private String content;

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
