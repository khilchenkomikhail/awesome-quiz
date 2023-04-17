package ru.edu.spbstu.quizproject.request;

public record AuthenticationRequest (
    String username,
    String password)
{ }
