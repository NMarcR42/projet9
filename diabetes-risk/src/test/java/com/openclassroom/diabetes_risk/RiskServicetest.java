package com.openclassroom.diabetes_risk;

import com.openclassroom.diabetes_risk.model.Note;
import com.openclassroom.diabetes_risk.model.Patient;
import com.openclassroom.diabetes_risk.model.RiskReport;
import com.openclassroom.diabetes_risk.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RiskServiceTest {

    private RiskService riskService;
    private WebClient webClientMock;

    @BeforeEach
    public void setUp() {
        webClientMock = mock(WebClient.class, RETURNS_DEEP_STUBS);
        riskService = new RiskService(webClientMock,
                "http://localhost:8081/patients",
                "http://localhost:8082/notes");
    }

    @Test
    public void testAssessRisk_Borderline() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("Doe");
        patient.setPrenom("John");
        patient.setDateNaissance("1990-01-01"); 
        patient.setGenre("M");

        Note note = new Note(1, "Patient fumeur et cholestérol anormal");

        when(webClientMock.get().uri("http://localhost:8081/patients/1").retrieve().bodyToMono(Patient.class))
                .thenReturn(Mono.just(patient));

        when(webClientMock.get().uri("http://localhost:8082/notes/1").retrieve().bodyToFlux(Note.class))
                .thenReturn(Flux.just(note));

        RiskReport report = riskService.assessRisk("1");

        assertEquals("1", report.getPatientId());
        assertEquals("John Doe", report.getFullName());
        assertEquals("M", report.getGenre());
        assertEquals("Borderline", report.getRisk()); 
    }

    @Test
    public void testAssessRisk_InDanger_YoungMale() {
        Patient patient = new Patient();
        patient.setId(2L);
        patient.setNom("Smith");
        patient.setPrenom("Alex");
        patient.setDateNaissance(LocalDate.now().minusYears(25).toString()); 
        patient.setGenre("M");

        List<Note> notes = List.of(
                new Note(1, "fumeur"),
                new Note(2, "anormal"),
                new Note(3, "cholestérol")
        );

        when(webClientMock.get().uri("http://localhost:8081/patients/2").retrieve().bodyToMono(Patient.class))
                .thenReturn(Mono.just(patient));

        when(webClientMock.get().uri("http://localhost:8082/notes/2").retrieve().bodyToFlux(Note.class))
                .thenReturn(Flux.fromIterable(notes));

        RiskReport report = riskService.assessRisk("2");

        assertEquals("InDanger", report.getRisk());
    }
}
