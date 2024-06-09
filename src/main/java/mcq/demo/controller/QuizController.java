package mcq.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mcq.demo.QuestionWrapper;
import mcq.demo.Quiz;
import mcq.demo.UserResponse;
import mcq.demo.service.QuizService;

@RestController

@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam Integer numQ,
            @RequestParam String title) {
        return quizService.createAQuiz(category, numQ, title);
    }

    @GetMapping("{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Integer id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("grade/{id}")
    public ResponseEntity<Integer> gradeQuiz(@PathVariable Integer id, @RequestBody List<UserResponse> userResponses) {
        return quizService.gradeQuiz(id, userResponses);
    }
}
