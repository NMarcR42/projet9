package com.openclassroom.diabetes_risk.service;

import com.openclassroom.diabetes_risk.model.Note;
import com.openclassroom.diabetes_risk.model.Patient;
import com.openclassroom.diabetes_risk.model.RiskReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RiskService {

    @Value("${patient.service.url}")
    private String patientServiceUrl;

    @Value("${notes.service.url}")
    private String notesServiceUrl;

    private final WebClient webClient = WebClient.create();

    public RiskReport assessRisk(Integer patientId) {
        // 1. Find the patient
        Patient patient = webClient.get()
                .uri(patientServiceUrl + "/" + patientId)
                .retrieve()
                .bodyToMono(Patient.class)
                .block();

        // 2. find the note
        List<Note> notes = webClient.get()
                .uri(notesServiceUrl + "/" + patientId)
                .retrieve()
                .bodyToFlux(Note.class)
                .collectList()
                .block();

        // 3. Trigger
        List<String> triggers = List.of(
                "hémoglobine a1c", "microalbumine", "taille", "poids",
                "fumeur", "fumeuse", "anormal", "cholestérol",
                "vertiges", "rechute", "réaction", "anticorps"
        );

        // 4. Trigger count
        int triggerCount = 0;
        for (Note note : notes) {
            String content = note.getContent().toLowerCase();
            for (String trigger : triggers) {
                if (content.contains(trigger.toLowerCase())) {
                    triggerCount++;
                }
            }
        }

        // 5. Age
        int age = Period.between(LocalDate.parse(patient.getDateNaissance()), LocalDate.now()).getYears();

        // 6. Risk 
        String risk = evaluateRisk(patient.getGenre(), age, triggerCount);

        String fullName = patient.getPrenom() + " " + patient.getNom();
        return new RiskReport(patientId, fullName, age, patient.getGenre(), risk);
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
