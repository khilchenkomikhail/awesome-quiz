package ru.edu.spbstu.quizproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edu.spbstu.quizproject.container.ResultList;
import ru.edu.spbstu.quizproject.model.Result;
import ru.edu.spbstu.quizproject.user.UserService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResultList> getUserResults() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Result> resultsByUserId1 = userService.getResultsByUserId(username);
        ResultList resultsByUserId = new ResultList();
        resultsByUserId.addAll(resultsByUserId1);
        return ResponseEntity.ok(resultsByUserId);
    }
}
