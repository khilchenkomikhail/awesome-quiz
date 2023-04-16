package ru.edu.spbstu.quizproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.spbstu.quizproject.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
