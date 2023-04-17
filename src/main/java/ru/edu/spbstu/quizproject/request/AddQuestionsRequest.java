package ru.edu.spbstu.quizproject.request;

import ru.edu.spbstu.quizproject.request.element.QuestionDTO;

import java.util.List;

public record AddQuestionsRequest(
        String quizName,
        List<QuestionDTO> questions
) { }
