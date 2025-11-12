package com.openslaroom.patient_back.service;

import com.openslaroom.patient_back.model.Patient;
import com.openslaroom.patient_back.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repo;

    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return repo.findById(id);
    }

    public Patient createPatient(Patient patient) {
        return repo.save(patient);
    }

    public Patient updatePatient(Long id, Patient patient) {
        return repo.findById(id)
                .map(p -> {
                    p.setNom(patient.getNom());
                    p.setPrenom(patient.getPrenom());
                    p.setDateNaissance(patient.getDateNaissance());
                    p.setGenre(patient.getGenre());
                    p.setAdresse(patient.getAdresse());
                    p.setTelephone(patient.getTelephone());
                    return repo.save(p);
                }).orElse(null);
    }

    public boolean deletePatient(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
