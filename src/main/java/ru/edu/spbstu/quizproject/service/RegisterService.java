package ru.edu.spbstu.quizproject.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.spbstu.quizproject.config.JwtService;
import ru.edu.spbstu.quizproject.exception.TakenNameException;
import ru.edu.spbstu.quizproject.user.UserRepository;
import ru.edu.spbstu.quizproject.mail.EmailVerification;
import ru.edu.spbstu.quizproject.mail.token.ConfirmationTokenService;
import ru.edu.spbstu.quizproject.request.AuthenticationRequest;
import ru.edu.spbstu.quizproject.request.RegisterRequest;
import ru.edu.spbstu.quizproject.response.AuthenticationResponse;
import ru.edu.spbstu.quizproject.user.User;
import ru.edu.spbstu.quizproject.user.UserRole;

import java.util.Optional;

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
        if (!emailVerification.checkEmail(request.email())) {
            throw new RuntimeException("BadEmail");
        }

        Optional<User> byUsername = userRepository.findByUsername(request.username());
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if (byUsername.isPresent() || byEmail.isPresent()) {
            throw new TakenNameException("Username or email is already taken");
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
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
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsername(request.username()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
