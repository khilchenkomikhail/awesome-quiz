package ru.edu.spbstu.quizproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.spbstu.quizproject.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
