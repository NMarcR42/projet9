package com.openclassroom.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.openclassroom.frontend.service.PatientService;
import com.openclassroom.frontend.model.RiskReport;
import com.openclassroom.frontend.model.Note;
import com.openclassroom.frontend.model.Patient;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gateway.url:http://localhost:8082}") // port gateway
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

        // Risque diabète
        try {
            RiskReport risk = restTemplate.getForObject(gatewayUrl + "/risk/" + id, RiskReport.class);
            model.addAttribute("risk", risk);
        } catch (Exception e) {
            model.addAttribute("risk", null);
        }

        // Notes du médecin
        try {
            Note[] notesArr = restTemplate.getForObject(gatewayUrl + "/notes/" + id, Note[].class);
            List<Note> notes = (notesArr != null) ? Arrays.asList(notesArr) : List.of();
            model.addAttribute("notes", notes);
        } catch (Exception e) {
            model.addAttribute("notes", List.of());
        }

        model.addAttribute("newNote", new Note());

        return "patient_details";
    }

    @GetMapping("/{id}/notes/add")
    public String showAddNoteForm(@PathVariable Long id, Model model) {
        model.addAttribute("patientId", id);
        model.addAttribute("note", new Note());
        return "note_form";
    }

    @PostMapping("/{id}/notes")
    public String addNoteForPatient(@PathVariable Long id, @ModelAttribute("note") Note note) {
        note.setPatientId(String.valueOf(id));
        restTemplate.postForObject(gatewayUrl + "/notes", note, Note.class);
        return "redirect:http://localhost:8082/frontend/patients/" + id;
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
        return "redirect:http://localhost:8082/frontend/patients"; 
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        service.updatePatient(id, patient);
        return "redirect:http://localhost:8082/frontend/patients"; 
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return "redirect:http://localhost:8082/frontend/patients"; 
    }
}
