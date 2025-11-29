package com.openclassroom.frontend.controller;

import com.openclassroom.frontend.service.PatientService;
import com.openclassroom.frontend.model.Patient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    void testListPatients() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        p.setNom("Doe");
        p.setPrenom("John");

        when(patientService.getAllPatients()).thenReturn(List.of(p));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("patients", List.of(p)));
    }
}
