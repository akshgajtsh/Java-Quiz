package quiz.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import quiz.entity.QuizFourChoices;

public interface QuizFourChoicesRepository extends CrudRepository<QuizFourChoices, Integer> {
	@Query("SELECT * FROM quiz_four_choices ORDER BY RANDOM() limit 1")
	Optional<QuizFourChoices> selectRandomOne();
}
