package quiz.service;

import java.util.List;
import java.util.Optional;

import quiz.entity.Quiz;

public interface QuizService {
	/*クイズ情報全件取得*/
	Iterable<Quiz>selectAll();

	/*クイズ１件取得*/
	Optional<Quiz>selectOneById(Integer id);

	/*クイズランダム取得*/
	Optional<Quiz>selectOneRandom();
	
	/*判定*/
	Boolean checkQuiz(Integer id, Boolean myAnswer);
	
	/*新規登録*/
	void insertQuiz(Quiz quiz);
	
	/*更新*/
	void updateQuiz(Quiz quiz);
	
	/*削除*/
	void deleteQuizById(Integer id);
	
	/*10件ランダム取得*/
	List<Quiz> select10Random();

}
