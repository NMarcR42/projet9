package com.openclassroom.patient_back.controller;

import com.openclassroom.patient_back.model.Patient;
import com.openclassroom.patient_back.service.PatientService;
import com.openclassroom.patient_back.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    @GetMapping
    public List<Patient> getAllPatients() {
        return service.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return service.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid PatientDTO patientDTO) {
        Patient patient = mapDtoToPatient(patientDTO);
        Patient saved = service.createPatient(patient);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody @Valid PatientDTO patientDTO) {
        Patient patient = mapDtoToPatient(patientDTO);
        Patient updated = service.updatePatient(id, patient);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (service.deletePatient(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Méthode utilitaire pour mapper notre DTO
    private Patient mapDtoToPatient(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setNom(dto.getNom());
        patient.setPrenom(dto.getPrenom());
        patient.setGenre(dto.getGenre());
        patient.setDateNaissance(dto.getDateNaissance());
        patient.setAdresse(dto.getAdresse());
        patient.setTelephone(dto.getTelephone());
        return patient;
    }
}
