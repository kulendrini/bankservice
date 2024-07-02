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


    /**
     * Performs a health check on the bank service and returns the current status of the service.
     *
     * This endpoint checks if the bank service is up and running and returns a {@link HealthCheckResponseDTO} containing
     * the status message and the current timestamp. This is useful for monitoring the health of the service.
     *
     * @return a {@link ResponseEntity} containing a {@link HealthCheckResponseDTO} with the status and timestamp of the health check
     *         and an HTTP status code of 200 OK if the service is running:
     *         <ul>
     *             <li>200 OK indicating that the health check was successful</li>
     *         </ul>
     * @see HealthCheckResponseDTO
     */
    @GetMapping(value = "/healthCheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HealthCheckResponseDTO> healthCheck() {
        HealthCheckResponseDTO response = new HealthCheckResponseDTO();
        response.setStatus("Running bank-service....!");
        response.setTimestamp(new Date());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
