package ru.edu.spbstu.quizproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@AllArgsConstructor
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> satHello() {
        return ResponseEntity.ok("Hello from demo");
    }
}
