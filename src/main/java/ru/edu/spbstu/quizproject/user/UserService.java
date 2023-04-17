package ru.edu.spbstu.quizproject.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.edu.spbstu.quizproject.exception.ObjectNotFoundException;
import ru.edu.spbstu.quizproject.model.Result;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long getIdByNameI(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username))
                .getId();
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public List<Result> getResultsByUserId(String username) {
        User byId = userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Username: " + username));
        return byId.getResults();
    }

    public User getUserFromAuthentication(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
