package quiz.service;

import java.util.Optional;

import quiz.entity.QuizFourChoices;

public interface QuizFourChoicesService {

	/*全件取得*/
	Iterable<QuizFourChoices> selectAll();

	/*1件取得*/
	Optional<QuizFourChoices> selectOneById(Integer id);

	/*ランダム取得*/
	Optional<QuizFourChoices> selectOneRandom();

	/*正誤判定*/
	boolean checkAnswer(Integer id, Integer userAnswer);

	/*新規登録*/
	void insertQuizFourChoices(QuizFourChoices quizFourChoices);
	
	/*更新*/
	void updateQuizFourChoices(QuizFourChoices quizFourChoices);

	/*削除*/
	void deleteQuizFourChoices(Integer id);

}
