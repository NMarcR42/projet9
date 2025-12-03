package com.openclassroom.diabetes_risk;

import com.openclassroom.diabetes_risk.controller.RiskController;
import com.openclassroom.diabetes_risk.model.RiskReport;
import com.openclassroom.diabetes_risk.service.RiskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RiskController.class)
@AutoConfigureMockMvc(addFilters = false)
class RiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRiskReport() throws Exception {
        RiskReport report = new RiskReport("1", "John Doe", 25, "M", "InDanger");

        Mockito.when(riskService.assessRisk(anyString())).thenReturn(report);

        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.genre").value("M"))
                .andExpect(jsonPath("$.risk").value("InDanger"));
    }
}
