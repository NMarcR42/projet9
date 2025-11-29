package com.sprint2.doctor_notes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint2.doctor_notes.model.Note;
import com.sprint2.doctor_notes.dto.NoteDTO;
import com.sprint2.doctor_notes.service.NoteService;
import com.sprint2.doctor_notes.controller.NoteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.authentication.AuthenticationManager;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false) 
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddNote() throws Exception {
        NoteDTO noteDto = new NoteDTO();
        noteDto.setPatientId(10L);
        noteDto.setContent("Test");

        Note note = new Note();
        note.setId("1");
        note.setPatientId(10L);
        note.setContent("Test");

        when(noteService.patientExists(10L)).thenReturn(true);
        when(noteService.addNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void testCreateNotePatientUnknown() throws Exception {
        Note note = new Note();
        note.setPatientId(0L);
        note.setContent("Test");

        when(noteService.patientExists(anyLong())).thenReturn(false);

        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetNotesByPatient() throws Exception {
        List<Note> notes = List.of(new Note(), new Note());

        when(noteService.getNotesByPatient(10L)).thenReturn(notes);

        mockMvc.perform(get("/notes/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
