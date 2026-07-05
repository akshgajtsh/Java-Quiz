package quiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quiz.entity.Quiz;
import quiz.repository.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	QuizRepository quizRepository;

	@Override
	public Iterable<Quiz> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return quizRepository.findAll();
	}

	@Override
	public Optional<Quiz> selectOneById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return quizRepository.findById(id);
	}

	@Override
	public Optional<Quiz> selectOneRandom() {
		// TODO 自動生成されたメソッド・スタブ
		Integer randId = quizRepository.getRandomId();
		if(randId == null) {
			return Optional.empty();
		}
		return quizRepository.findById(randId);
	}

	@Override
	public Boolean checkQuiz(Integer id, Boolean myAnswer) {
		// TODO 自動生成されたメソッド・スタブ
		Boolean check = false;
		Optional<Quiz>optQuiz = quizRepository.findById(id);
		if(optQuiz.isPresent()) {
			Quiz quiz = optQuiz.get();
		   
			if(quiz.getAnswer().equals(myAnswer)) {
				check = true;
			}
		}
		return check;
	}

	@Override
	public void insertQuiz(Quiz quiz) {
		// TODO 自動生成されたメソッド・スタブ
		quizRepository.save(quiz);

	}

	@Override
	public void updateQuiz(Quiz quiz) {
		// TODO 自動生成されたメソッド・スタブ
		quizRepository.save(quiz);

	}

	@Override
	public void deleteQuizById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		quizRepository.deleteById(id);

	}

}
