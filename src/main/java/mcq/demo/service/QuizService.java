package mcq.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mcq.demo.Question;
import mcq.demo.QuestionWrapper;
import mcq.demo.Quiz;
import mcq.demo.UserResponse;
import mcq.demo.dao.QuestionDao;
import mcq.demo.dao.QuizDao;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createAQuiz(String category, Integer numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);

        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<Quiz> getQuizById(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);

        if (quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if (quiz.isPresent()) {
            // lets get the questions
            List<Question> questionFromDatabase = quiz.get().getQuestions();
            // lets convert the question to the question wrapper
            List<QuestionWrapper> questionForUser = new ArrayList<>();

            for (Question q : questionFromDatabase) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),
                        q.getOption2(), q.getOption3(), q.getOption4(), q.getCategory(), q.getDifficultLevel());
                questionForUser.add(qw);
            }
            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<Integer> gradeQuiz(Integer id, List<UserResponse> userResponses) {
        // lets get the quiz
        Optional<Quiz> quiz = quizDao.findById(id);
        if (quiz.isPresent()) {
            // lets get the questions for the quiz
            List<Question> questions = quiz.get().getQuestions();
            // lets ensure that the user answer all the questions
            if (questions.size() != userResponses.size()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            int counter = 0;
            int i = 0;
            // lets get the array of the right answer
            for (Question q : questions) {
                String answer = q.getRightAnswer();
                String userAnswer = userResponses.get(i).getResponse();
                if (answer != null && answer.equals(userAnswer)) {
                    counter++;
                }
                i++;
            }
            return new ResponseEntity<>(counter, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
