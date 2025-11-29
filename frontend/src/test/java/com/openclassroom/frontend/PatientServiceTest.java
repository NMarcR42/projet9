package com.openclassroom.frontend.service;

import com.openclassroom.frontend.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

class PatientServiceTest {

    private PatientService patientService;
    private RestTemplate restTemplateMock;

    @BeforeEach
    void setup() {
        restTemplateMock = mock(RestTemplate.class);
        patientService = new PatientService();
        try {
            var field = PatientService.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(patientService, restTemplateMock);

            var urlField = PatientService.class.getDeclaredField("gatewayUrl");
            urlField.setAccessible(true);
            urlField.set(patientService, "http://localhost:8082");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllPatients() {
        Patient p = new Patient();
        p.setId(1L);
        p.setNom("Doe");
        p.setPrenom("John");
        when(restTemplateMock.getForObject("http://localhost:8082/patients", Patient[].class))
                .thenReturn(new Patient[]{p});

        List<Patient> patients = patientService.getAllPatients();
        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getPrenom());
    }
}
