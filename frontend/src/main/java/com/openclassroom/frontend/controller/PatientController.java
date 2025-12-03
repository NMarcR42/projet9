package com.openclassroom.frontend.controller;

import com.openclassroom.frontend.service.PatientService;
import com.openclassroom.frontend.model.Patient;
import com.openclassroom.frontend.model.Note;
import com.openclassroom.frontend.model.RiskReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", service.getAllPatients());
        return "patients";
    }

    @GetMapping("/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {

        Patient patient = service.getPatientById(id);
        model.addAttribute("patient", patient);

        try {
            RiskReport risk = restTemplate.getForObject(gatewayUrl + "/risk/" + id, RiskReport.class);
            model.addAttribute("risk", risk);
        } catch (Exception e) {
            model.addAttribute("risk", null);
        }

        try {
            Note[] notesArr = restTemplate.getForObject(gatewayUrl + "/notes/" + id, Note[].class);
            List<Note> notes = notesArr != null ? Arrays.asList(notesArr) : List.of();
            model.addAttribute("notes", notes);
        } catch (Exception e) {
            model.addAttribute("notes", List.of());
        }

        model.addAttribute("newNote", new Note());

        return "patient_details";
    }

    @PostMapping("/{id}/notes")
    public String addNoteForPatient(@PathVariable Long id, @ModelAttribute Note note) {
        note.setPatientId(String.valueOf(id));
        restTemplate.postForObject(gatewayUrl + "/notes", note, Note.class);
        return "redirect:/patients/" + id;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient_form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", service.getPatientById(id));
        return "patient_form";
    }

    @PostMapping
    public String addPatient(@ModelAttribute Patient patient) {
        service.createPatient(patient);
        return "redirect:/patients";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        service.updatePatient(id, patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return "redirect:/patients";
    }
}
