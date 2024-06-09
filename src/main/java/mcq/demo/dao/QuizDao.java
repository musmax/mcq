package mcq.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mcq.demo.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
