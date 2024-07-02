package com.app.bankservice.controller;

import com.app.bankservice.model.LoanResponseDTO;
import com.app.bankservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/dbservice/app")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/loan/{username}")
    public ResponseEntity<LoanResponseDTO> getLoan(@PathVariable("username") String username) {
        return ResponseEntity.ok(loanService.getLoanDetailsByUserName(username));
    }
}
