package mcq.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mcq.demo.Question;
import mcq.demo.dao.QuestionDao;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionDao.findQuestionByCategory(category);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        String body = "success";
        return new ResponseEntity<> (body, HttpStatus.CREATED);
    }

    public Question getQuestionById(Integer id) {
        return questionDao.findById(id).orElseThrow(() -> new IllegalStateException("Question not found"));
    }

    public void deleteQuestionById(Integer id) {
        // lets see if question exists
        boolean exists = questionDao.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Question not found");
        }
        questionDao.deleteById(id);
    }

}
