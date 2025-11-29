package com.sprint2.doctor_notes.repository;

import com.sprint2.doctor_notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatientId(Long patientId);
}
