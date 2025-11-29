package com.openclassroom.diabetes_risk;

import com.openclassroom.diabetes_risk.model.Note;
import com.openclassroom.diabetes_risk.model.Patient;
import com.openclassroom.diabetes_risk.model.RiskReport;
import com.openclassroom.diabetes_risk.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
class RiskServiceTest {

    private RiskService riskService;
    private WebClient webClientMock;

    @BeforeEach
    void setup() throws Exception {
        webClientMock = mock(WebClient.class, RETURNS_DEEP_STUBS);
        riskService = new RiskService();

        var field = RiskService.class.getDeclaredField("webClient");
        field.setAccessible(true);
        field.set(riskService, webClientMock);
    }

    @Test
    void testAssessRisk() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setPrenom("John");
        patient.setNom("Doe");
        patient.setDateNaissance("2000-01-01");
        patient.setGenre("M");

        
        Note note1 = new Note(); note1.setContent("fumeur");
        Note note2 = new Note(); note2.setContent("cholestérol");
        Note note3 = new Note(); note3.setContent("microalbumine");

        when(webClientMock.get().uri(anyString()).retrieve().bodyToMono(Patient.class).block())
                .thenReturn(patient);

        when(webClientMock.get().uri(anyString()).retrieve().bodyToFlux(Note.class).collectList().block())
                .thenReturn(List.of(note1, note2, note3));

        RiskReport report = riskService.assessRisk(1);

        assertEquals("John Doe", report.getFullName());
        assertEquals("M", report.getGenre());
        assertEquals("InDanger", report.getRisk());
    }
}
