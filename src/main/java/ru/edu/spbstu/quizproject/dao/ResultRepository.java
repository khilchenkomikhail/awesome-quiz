package ru.edu.spbstu.quizproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.spbstu.quizproject.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
