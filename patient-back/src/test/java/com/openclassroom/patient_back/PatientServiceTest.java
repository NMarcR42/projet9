package com.openclassroom.patient_back;

import com.openclassroom.patient_back.model.Patient;
import com.openclassroom.patient_back.repository.PatientRepository;
import com.openclassroom.patient_back.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private PatientRepository patientRepository;
    private PatientService patientService;

    @BeforeEach
    void setup() {
        patientRepository = mock(PatientRepository.class);
        patientService = new PatientService(patientRepository);
    }

    @Test
    void testGetAllPatients() {
        Patient p1 = new Patient(); p1.setId(1L);
        Patient p2 = new Patient(); p2.setId(2L);
        when(patientRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Patient> result = patientService.getAllPatients();
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById() {
        Patient p = new Patient(); p.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Patient> result = patientService.getPatientById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testCreatePatient() {
        Patient p = new Patient(); p.setNom("John");
        when(patientRepository.save(p)).thenReturn(p);

        Patient result = patientService.createPatient(p);
        assertEquals("John", result.getNom());
        verify(patientRepository, times(1)).save(p);
    }

    @Test
    void testUpdatePatientFound() {
        Patient existing = new Patient(); existing.setId(1L); existing.setNom("Old");
        Patient update = new Patient(); update.setNom("New");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(patientRepository.save(existing)).thenReturn(existing);

        Patient result = patientService.updatePatient(1L, update);
        assertEquals("New", result.getNom());
    }

    @Test
    void testUpdatePatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        Patient result = patientService.updatePatient(1L, new Patient());
        assertNull(result);
    }

    @Test
    void testDeletePatient() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        boolean result = patientService.deletePatient(1L);
        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePatientNotFound() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        boolean result = patientService.deletePatient(1L);
        assertFalse(result);
        verify(patientRepository, never()).deleteById(anyLong());
    }
}
