package ru.edu.spbstu.quizproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.spbstu.quizproject.user.User;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.isEnabled = TRUE where u.email = ?1")
    int enableUser(String email);
}
