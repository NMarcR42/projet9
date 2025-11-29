package com.sprint2.doctor_notes.controller;

import com.sprint2.doctor_notes.model.Note;
import com.sprint2.doctor_notes.dto.NoteDTO;
import com.sprint2.doctor_notes.service.NoteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notes")
@Validated
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note createNote(@Valid @RequestBody NoteDTO noteDto) {
        // Vérification de l'existence du patient
        if (!noteService.patientExists(noteDto.getPatientId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient inconnu");
        }

        Note note = new Note();
        note.setPatientId(noteDto.getPatientId());
        note.setContent(noteDto.getContent());
        note.setDate(LocalDate.now().toString());

        return noteService.addNote(note);
    }

    @GetMapping("/{patientId}")
    public List<Note> getNotes(@PathVariable Long patientId) {
        return noteService.getNotesByPatient(patientId);
    }
}
