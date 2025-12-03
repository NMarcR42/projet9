package com.openclassroom.patient_back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.patient_back.controller.PatientController;
import com.openclassroom.patient_back.dto.PatientDTO;
import com.openclassroom.patient_back.model.Patient;
import com.openclassroom.patient_back.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPatients() throws Exception {
        Patient p1 = new Patient(); p1.setId(1L); p1.setNom("John");
        Patient p2 = new Patient(); p2.setId(2L); p2.setNom("Jane");

        when(patientService.getAllPatients()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/patients").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }


    @Test
    void testGetPatientByIdFound() throws Exception {
        Patient p = new Patient(); p.setId(1L); p.setNom("John");
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John"));
    }

    @Test
    void testCreatePatient() throws Exception {
        PatientDTO dto = new PatientDTO();
        dto.setNom("NewPatient");
        dto.setPrenom("Test");
        dto.setGenre("H");
        dto.setDateNaissance("2000-01-01");
        dto.setAdresse("123 Rue Test");
        dto.setTelephone("0123456789");

        Patient saved = new Patient();
        saved.setId(1L);
        saved.setNom(dto.getNom());
        saved.setPrenom(dto.getPrenom());
        saved.setGenre(dto.getGenre());
        saved.setDateNaissance(dto.getDateNaissance());
        saved.setAdresse(dto.getAdresse());
        saved.setTelephone(dto.getTelephone());

        when(patientService.createPatient(any(Patient.class))).thenReturn(saved);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("NewPatient"));
    }
}
