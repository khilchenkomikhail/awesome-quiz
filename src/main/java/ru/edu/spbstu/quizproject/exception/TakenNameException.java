package ru.edu.spbstu.quizproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TakenNameException extends RuntimeException {
    public TakenNameException(String message) {
        super(message);
    }
}
