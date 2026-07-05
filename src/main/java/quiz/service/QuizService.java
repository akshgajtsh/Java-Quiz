package quiz.service;

import java.util.Optional;

import quiz.entity.Quiz;

public interface QuizService {
	/*クイズ情報全件取得*/
	Iterable<Quiz>selectAll();

	/*クイズ１件取得*/
	Optional<Quiz>selectOneById(Integer id);

	/*クイズランダム取得*/
	Optional<Quiz>selectOneRandom();
	
	Boolean checkQuiz(Integer id, Boolean myAnswer);
	
	void insertQuiz(Quiz quiz);
	void updateQuiz(Quiz quiz);
	void deleteQuizById(Integer id);

}
