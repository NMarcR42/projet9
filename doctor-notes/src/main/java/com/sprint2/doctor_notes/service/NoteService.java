package com.sprint2.doctor_notes.service;

import com.sprint2.doctor_notes.model.Note;
import com.sprint2.doctor_notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository)
    {
        this.noteRepository = noteRepository;
    }


    public Note addNote(Note note)
    {
        return noteRepository.save(note);
    }


    public List<Note> getNotesByPatient(String patientId)
    {
        return noteRepository.findByPatientId(patientId);
    }
}
