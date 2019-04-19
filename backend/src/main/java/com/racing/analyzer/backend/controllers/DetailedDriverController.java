package com.racing.analyzer.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface DetailedDriverController {
    
    @GetMapping("/races/{id}/detailedDriverData")
    ResponseEntity<?> getDetailedDriverList(@PathVariable long id);

    @GetMapping("/races/{id}/detailedDriverData/{number}")
    ResponseEntity<?> getDetailedDriver(@PathVariable long id, @PathVariable int number);
}
