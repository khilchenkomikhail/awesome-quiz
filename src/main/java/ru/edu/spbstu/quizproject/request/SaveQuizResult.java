package ru.edu.spbstu.quizproject.request;

public record SaveQuizResult (
        String quizName,
        Integer percent
)
{}
