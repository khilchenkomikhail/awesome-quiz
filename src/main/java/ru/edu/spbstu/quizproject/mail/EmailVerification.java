package ru.edu.spbstu.quizproject.mail;

import org.springframework.stereotype.Component;

@Component
public interface EmailVerification {
    Boolean checkEmail(String email);
}
