package com.openclassroom.diabetes_risk;

import com.openclassroom.diabetes_risk.controller.RiskController;
import com.openclassroom.diabetes_risk.model.RiskReport;
import com.openclassroom.diabetes_risk.service.RiskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.openclassroom.diabetes_risk.config.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RiskController.class)
@Import(SecurityConfig.class)
class RiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRiskReport() throws Exception {
        RiskReport report = new RiskReport(1, "John Doe", 25, "M", "InDanger");

        Mockito.when(riskService.assessRisk(anyInt())).thenReturn(report);

        mockMvc.perform(get("/risk/1")
                        .with(httpBasic("doctor", "password"))) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.genre").value("M"))
                .andExpect(jsonPath("$.risk").value("InDanger"));
    }
}
