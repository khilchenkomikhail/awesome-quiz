package ru.edu.spbstu.quizproject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.spbstu.quizproject.dao.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
