package com.openclassroom.frontend.service;

import com.openclassroom.frontend.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    @Value("${gateway.url}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    public PatientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Patient> getAllPatients() {
        Patient[] patients = restTemplate.getForObject(gatewayUrl + "/patients", Patient[].class);
        return Arrays.asList(patients);
    }

    public Patient getPatientById(Long id) {
        return restTemplate.getForObject(gatewayUrl + "/patients/" + id, Patient.class);
    }

    public void createPatient(Patient patient) {
        restTemplate.postForObject(gatewayUrl + "/patients", patient, Patient.class);
    }

    public void updatePatient(Long id, Patient patient) {
        restTemplate.put(gatewayUrl + "/patients/" + id, patient);
    }

    public void deletePatient(Long id) {
        restTemplate.delete(gatewayUrl + "/patients/" + id);
    }
}
