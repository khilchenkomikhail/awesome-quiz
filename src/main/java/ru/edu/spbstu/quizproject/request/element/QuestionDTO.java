package ru.edu.spbstu.quizproject.request.element;

import java.util.List;

public record QuestionDTO(
        String question,
        List<String> correctAnswers,
        List<String> wrongAnswers
) {}
