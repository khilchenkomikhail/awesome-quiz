package ru.edu.spbstu.quizproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomIllegalException extends RuntimeException{
    public CustomIllegalException(String message) {
        super(message);
    }
}
