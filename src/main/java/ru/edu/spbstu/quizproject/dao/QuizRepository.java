package ru.edu.spbstu.quizproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.spbstu.quizproject.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> getAllByTheme(String theme);

    Optional<Quiz> findByName(String name);
}
