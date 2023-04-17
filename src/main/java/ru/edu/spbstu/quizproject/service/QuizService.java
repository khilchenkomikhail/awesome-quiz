package ru.edu.spbstu.quizproject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.spbstu.quizproject.dao.AnswerRepository;
import ru.edu.spbstu.quizproject.dao.QuestionRepository;
import ru.edu.spbstu.quizproject.dao.QuizRepository;
import ru.edu.spbstu.quizproject.dao.ResultRepository;
import ru.edu.spbstu.quizproject.exception.CustomIllegalException;
import ru.edu.spbstu.quizproject.exception.ObjectNotFoundException;
import ru.edu.spbstu.quizproject.model.Answer;
import ru.edu.spbstu.quizproject.model.Question;
import ru.edu.spbstu.quizproject.model.Quiz;
import ru.edu.spbstu.quizproject.model.Result;
import ru.edu.spbstu.quizproject.request.AddQuestionsRequest;
import ru.edu.spbstu.quizproject.request.QuizRequest;
import ru.edu.spbstu.quizproject.request.SaveQuizResult;
import ru.edu.spbstu.quizproject.request.element.QuestionDTO;
import ru.edu.spbstu.quizproject.user.User;
import ru.edu.spbstu.quizproject.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public List<Quiz> getFirst() {
        return quizRepository.findAll();
    }

    public List<Quiz> getByTheme(String theme) {
        return quizRepository.getAllByTheme(theme);
    }

    public Optional<Quiz> getByName(String name) {
        return quizRepository.findByName(name);
    }

    public void createQuiz(QuizRequest request, User user) {
        Quiz quiz = Quiz.builder()
                .isComplete(Boolean.FALSE)
                .theme(request.theme())
                .name(request.name())
                .user(user)
                .build();
        quizRepository.save(quiz);
    }

    public void addQuestions(AddQuestionsRequest request, User user) {
        Quiz quiz = quizRepository
                .findByName(request.quizName())
                .orElseThrow(()
                        -> new ObjectNotFoundException("Quiz by name not found " + request.quizName()));
        if (quiz.getIsComplete()) {
            throw new CustomIllegalException("Quiz is already complete, quiz name: " + quiz.getName());
        }

        List<QuestionDTO> questionDTOs = request.questions();
        List<Question> questions = new ArrayList<>();
        for (QuestionDTO dto : questionDTOs) {
            Question question = new Question();
            question.setQuestion(dto.question());
            List<Answer> correctAnswers = dto
                    .correctAnswers().stream().map(e -> {
                        Answer answer = Answer.builder().isCorrect(true).value(e).build();
                        answer = answerRepository.save(answer);
                        return answer;
                    }).toList();
            List<Answer> wrongAnswers = dto
                    .correctAnswers().stream().map(e -> {
                        Answer answer = Answer.builder().isCorrect(false).value(e).build();
                        answer = answerRepository.save(answer);
                        return answer;
                    }).toList();
            question.setCorrectAnswers(correctAnswers);
            question.setWrongAnswers(wrongAnswers);
            questions.add(questionRepository.save(question));
        }
        List<Question> questions1 = quiz.getQuestions();
        questions1.addAll(questions);
        quiz.setQuestions(questions1);
        quizRepository.save(quiz);
    }

    public void saveResult(SaveQuizResult request, User user) {
        Quiz quiz = quizRepository.findByName(request.quizName())
                .orElseThrow(() -> new ObjectNotFoundException("Quiz name: " + request.quizName()));

        Result result = Result.builder().quiz(quiz).percent(request.percent()).build();
        resultRepository.save(result);
        List<Result> tmp = user.getResults();
        tmp.add(result);
        user.setResults(tmp);
        userRepository.save(user);
    }

    public void finalizeQuiz(String quizName, User user) {
        Quiz quiz = quizRepository.findByName(quizName).orElseThrow(() -> new ObjectNotFoundException("Quiz: " + quizName));
        if (user.equals(quiz.getUser())) {
            throw new CustomIllegalException("Not your quiz");
        }
        quiz.setIsComplete(true);
        quizRepository.save(quiz);
    }
}
