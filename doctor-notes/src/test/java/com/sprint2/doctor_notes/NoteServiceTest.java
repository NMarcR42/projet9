package com.sprint2.doctor_notes;

import com.sprint2.doctor_notes.model.Note;
import com.sprint2.doctor_notes.service.NoteService;
import com.sprint2.doctor_notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NoteServiceTest {

    private NoteRepository noteRepository;
    private NoteService noteService;

    @BeforeEach
    void setup() {
        noteRepository = mock(NoteRepository.class);
        noteService = new NoteService(noteRepository);
    }

    @Test
    void testAddNote() {
        Note note = new Note();
        note.setId("1");
        note.setPatientId(123);
        note.setContent("Test note");

        when(noteRepository.save(note)).thenReturn(note);

        Note saved = noteService.addNote(note);

        assertEquals("1", saved.getId());
        assertEquals(123, saved.getPatientId());
    }

    @Test
    void testGetNotesByPatient() {
        int patientId = 123;

        List<Note> expectedList = List.of(
                new Note(patientId, "Note 1"),
                new Note(patientId, "Note 2")
        );

        when(noteRepository.findByPatientId(patientId)).thenReturn(expectedList);

        List<Note> result = noteService.getNotesByPatient(patientId);

        assertEquals(2, result.size());
        assertEquals("Note 1", result.get(0).getContent());
        verify(noteRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void testPatientExists() {
        assertTrue(noteService.patientExists(123));
        assertFalse(noteService.patientExists(0));
    }
}
