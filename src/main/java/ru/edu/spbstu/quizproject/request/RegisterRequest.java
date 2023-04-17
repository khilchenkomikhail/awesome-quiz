package ru.edu.spbstu.quizproject.request;


public record RegisterRequest(
    String username,
    String password,
    String email)
{}
