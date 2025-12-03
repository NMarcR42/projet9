package com.openclassroom.frontend;

import com.openclassroom.frontend.controller.PatientController;
import com.openclassroom.frontend.model.Patient;
import com.openclassroom.frontend.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService service;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testListPatients() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        p.setNom("Doe");
        p.setPrenom("John");

        when(service.getAllPatients()).thenReturn(List.of(p));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patients"))
                .andExpect(view().name("patients"));
    }
}
