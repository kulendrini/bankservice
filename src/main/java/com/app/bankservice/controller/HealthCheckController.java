package com.app.bankservice.controller;

import com.app.bankservice.model.HealthCheckResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("v1/dbservice/app")
public class HealthCheckController {

    @GetMapping(value = "/healthCheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HealthCheckResponseDTO> healthCheck() {
        HealthCheckResponseDTO response = new HealthCheckResponseDTO();
        response.setStatus("Running bank-service....!");
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
