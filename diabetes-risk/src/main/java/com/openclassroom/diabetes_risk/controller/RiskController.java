package com.openclassroom.diabetes_risk.controller;

import com.openclassroom.diabetes_risk.model.RiskReport;
import com.openclassroom.diabetes_risk.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @GetMapping("/{patientId}")
    public RiskReport getRisk(@PathVariable String patientId) {
        return riskService.assessRisk(patientId);
    }

    @GetMapping("/test")
    public String test() {
        return "diabetes-risk OK";
    }
}
