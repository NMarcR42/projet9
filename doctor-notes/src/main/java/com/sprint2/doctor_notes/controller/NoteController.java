package com.sprint2.doctor_notes.controller;

import com.sprint2.doctor_notes.model.Note;
import com.sprint2.doctor_notes.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        note.setDate(LocalDate.now().toString());
        return noteService.addNote(note);
    }

    @GetMapping("/{patientId}")
    public List<Note> getNotes(@PathVariable String patientId) {
        return noteService.getNotesByPatient(patientId);
    }
}
