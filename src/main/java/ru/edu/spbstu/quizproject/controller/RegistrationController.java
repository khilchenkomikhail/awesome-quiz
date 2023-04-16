package ru.edu.spbstu.quizproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.spbstu.quizproject.mail.token.ConfirmationTokenService;
import ru.edu.spbstu.quizproject.request.AuthenticationRequest;
import ru.edu.spbstu.quizproject.request.RegisterRequest;
import ru.edu.spbstu.quizproject.response.AuthenticationResponse;
import ru.edu.spbstu.quizproject.service.RegisterService;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegisterService registerService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(registerService.authenticate(request));
    }

    @GetMapping("/confirm-account")
    public String confirmEmail(@RequestParam(name = "token") String token) {
        return confirmationTokenService.confirmToken(token);
    }
}
