package com.app.bankservice.controller;

import com.app.bankservice.model.LoanResponseDTO;
import com.app.bankservice.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/dbservice/app")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/loan/{username}")
    public ResponseEntity<LoanResponseDTO> getLoan(@PathVariable("username") String username) {
        try {
            LoanResponseDTO loanResponseDTO = loanService.getLoanDetailsByUserName(username);
            return ResponseEntity.ok(loanResponseDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("Client error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected server error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
