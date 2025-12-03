package com.openclassroom.frontend.service;

import com.openclassroom.frontend.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientServiceTest {

    private PatientService patientService;
    private RestTemplate restTemplateMock;

    @BeforeEach
    void setup() {
        restTemplateMock = mock(RestTemplate.class);
        patientService = new PatientService(restTemplateMock);
    }

    @Test
    void testGetAllPatients() {
        Patient p = new Patient();
        p.setId(1L);
        p.setNom("Doe");
        p.setPrenom("John");

        when(restTemplateMock.getForObject(anyString(), eq(Patient[].class)))
                .thenReturn(new Patient[]{p});

        List<Patient> patients = patientService.getAllPatients();

        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getPrenom());
    }

}
