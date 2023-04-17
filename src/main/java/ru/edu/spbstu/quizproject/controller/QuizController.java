package ru.edu.spbstu.quizproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.spbstu.quizproject.container.QuizList;
import ru.edu.spbstu.quizproject.exception.TakenNameException;
import ru.edu.spbstu.quizproject.model.Quiz;
import ru.edu.spbstu.quizproject.request.AddQuestionsRequest;
import ru.edu.spbstu.quizproject.request.QuizRequest;
import ru.edu.spbstu.quizproject.request.SaveQuizResult;
import ru.edu.spbstu.quizproject.service.AnswerService;
import ru.edu.spbstu.quizproject.service.QuestionService;
import ru.edu.spbstu.quizproject.service.QuizService;
import ru.edu.spbstu.quizproject.user.User;
import ru.edu.spbstu.quizproject.user.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/quizzes")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final UserService userService;

    @GetMapping("/get-first")
    public ResponseEntity<QuizList> getFirstQuizzes() {
        List<Quiz> collect = quizService.getFirst()
                .stream()
                .filter(Quiz::getIsComplete).toList();
        QuizList quizList = new QuizList();
        quizList.addAll(collect);
        return ResponseEntity.ok(quizList);
    }

    @GetMapping("/get-by-theme")
    public ResponseEntity<QuizList> getByTheme(@RequestParam(name = "theme") String theme) {
        List<Quiz> collect = quizService.getByTheme(theme)
                .stream()
                .filter(Quiz::getIsComplete).toList();
        QuizList quizList = new QuizList();
        quizList.addAll(collect);
        return ResponseEntity.ok(quizList);
    }

    @PostMapping("/create-quiz")
    public ResponseEntity createQuiz(@RequestBody QuizRequest request) {
        Optional<Quiz> byName = quizService.getByName(request.name());
        if (byName.isPresent()) {
            throw new TakenNameException("Quiz with name " + request.name() + " already exists");
        }
        User user = userService.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        quizService.createQuiz(request, user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/add-question-to-quiz")
    public ResponseEntity addQuestions(@RequestBody AddQuestionsRequest request) {
        User user = userService.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        quizService.addQuestions(request, user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/finalize-quiz")
    public ResponseEntity finalizeQuiz(@RequestParam(name = "name") String quizName) {
        User user = userService.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        quizService.finalizeQuiz(quizName, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save-test-result")
    public ResponseEntity saveTestResult(@RequestBody SaveQuizResult request) {
        User user = userService.getUserFromAuthentication(SecurityContextHolder.getContext().getAuthentication());
        quizService.saveResult(request, user);
        return ResponseEntity.ok().build();
    }
}
