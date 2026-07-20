package quiz.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import quiz.entity.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, Integer> {
	@Query("SELECT id FROM quiz ORDER BY RANDOM() limit 1")
	Integer getRandomId();
	
	@Query("SELECT * FROM quiz ORDER BY RANDOM() limit 10")
	List<Quiz> getRandomQuiz();
}
