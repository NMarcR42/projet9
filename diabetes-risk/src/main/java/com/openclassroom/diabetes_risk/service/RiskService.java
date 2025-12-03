package com.openclassroom.diabetes_risk.service;

import com.openclassroom.diabetes_risk.model.Note;
import com.openclassroom.diabetes_risk.model.Patient;
import com.openclassroom.diabetes_risk.model.RiskReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RiskService {

    private String patientServiceUrl;
    private String notesServiceUrl;
    private WebClient webClient;

    public RiskService() {
        this.webClient = WebClient.create();
    }

    public RiskService(@Value("${patient.service.url}") String patientServiceUrl,
                       @Value("${notes.service.url}") String notesServiceUrl) {
        this.patientServiceUrl = patientServiceUrl;
        this.notesServiceUrl = notesServiceUrl;
        this.webClient = WebClient.create();
    }

    // Constructeur pour tests
    public RiskService(WebClient webClient, String patientServiceUrl, String notesServiceUrl) {
        this.webClient = webClient;
        this.patientServiceUrl = patientServiceUrl;
        this.notesServiceUrl = notesServiceUrl;
    }

    public RiskReport assessRisk(String patientId) {  
        Patient patient;
        List<Note> notes;

        try {
            patient = webClient.get()
                    .uri(patientServiceUrl + "/" + patientId)
                    .retrieve()
                    .bodyToMono(Patient.class)
                    .block();

            notes = webClient.get()
                    .uri(notesServiceUrl + "/" + patientId)
                    .retrieve()
                    .bodyToFlux(Note.class)
                    .collectList()
                    .block();

        } catch (WebClientResponseException e) {
            return new RiskReport(patientId, "Unknown", 0, "Unknown", "Unknown");
        } catch (Exception e) {
            return new RiskReport(patientId, "Unknown", 0, "Unknown", "Unknown");
        }

        if (patient == null || notes == null) {
            return new RiskReport(patientId, "Unknown", 0, "Unknown", "Unknown");
        }

        int triggerCount = countTriggers(notes);

        int age = Period.between(LocalDate.parse(patient.getDateNaissance()), LocalDate.now()).getYears();

        String risk = evaluateRisk(patient.getGenre(), age, triggerCount);

        String fullName = patient.getPrenom() + " " + patient.getNom();
        return new RiskReport(patientId, fullName, age, patient.getGenre(), risk);
    }

    private int countTriggers(List<Note> notes) {
        List<String> triggers = List.of(
                "hémoglobine a1c", "microalbumine", "taille", "poids",
                "fumeur", "fumeuse", "anormal", "cholestérol",
                "vertiges", "rechute", "réaction", "anticorps"
        );

        int count = 0;
        for (Note note : notes) {
            String content = note.getContent().toLowerCase();
            for (String trigger : triggers) {
                if (content.contains(trigger.toLowerCase())) {
                    count++;
                }
            }
        }
        return count;
    }

    private String evaluateRisk(String sex, int age, int triggers) {
        sex = sex.toUpperCase();

        if (triggers == 0) return "None";
        if (triggers >= 2 && triggers <= 5 && age > 30) return "Borderline";
        if ((sex.equals("M") && age < 30 && triggers >= 3 && triggers <= 5)
                || (sex.equals("F") && age < 30 && triggers >= 4 && triggers <= 6)
                || (age > 30 && triggers >= 6 && triggers <= 7)) return "InDanger";
        if ((sex.equals("M") && age < 30 && triggers >= 5)
                || (sex.equals("F") && age < 30 && triggers >= 7)
                || (age > 30 && triggers >= 8)) return "EarlyOnset";

        return "None";
    }
}
