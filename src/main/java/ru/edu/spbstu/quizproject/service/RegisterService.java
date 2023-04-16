package ru.edu.spbstu.quizproject.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.spbstu.quizproject.config.JwtService;
import ru.edu.spbstu.quizproject.dao.UserRepository;
import ru.edu.spbstu.quizproject.mail.EmailVerification;
import ru.edu.spbstu.quizproject.mail.token.ConfirmationTokenService;
import ru.edu.spbstu.quizproject.request.AuthenticationRequest;
import ru.edu.spbstu.quizproject.request.RegisterRequest;
import ru.edu.spbstu.quizproject.response.AuthenticationResponse;
import ru.edu.spbstu.quizproject.user.User;
import ru.edu.spbstu.quizproject.user.UserRole;

@Service
@AllArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final ConfirmationTokenService tokenService;
    private final EmailVerification emailVerification;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (!emailVerification.checkEmail(request.getEmail())) {
            throw new RuntimeException("BadEmail");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userRole(UserRole.USER)
                .build();
        user = userRepository.save(user);

        tokenService.sendConfirmationToken(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
