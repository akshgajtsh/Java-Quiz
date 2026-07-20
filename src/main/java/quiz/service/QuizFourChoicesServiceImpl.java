package quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import quiz.entity.Quiz;
import quiz.entity.QuizFourChoices;
import quiz.repository.QuizFourChoicesRepository;

@Service
@Transactional
public class QuizFourChoicesServiceImpl implements QuizFourChoicesService {

	@Autowired
	private QuizFourChoicesRepository repository;
	
	@Override
	public Iterable<QuizFourChoices> selectAll() {
		return repository.findAll();
	}

	@Override
	public Optional<QuizFourChoices> selectOneById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Optional<QuizFourChoices> selectOneRandom() {
		return repository.selectRandomOne();
	}

	@Override
	public boolean checkAnswer(Integer id, Integer userAnswer) {
		Optional<QuizFourChoices> optQuiz = repository.findById(id);
		if(optQuiz.isPresent()) {
			QuizFourChoices quizFourChoices = optQuiz.get();
			if(quizFourChoices.getAnswer().equals(userAnswer)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void insertQuizFourChoices(QuizFourChoices quizFourChoices) {
	    repository.save(quizFourChoices);
	}

	@Override
	public void updateQuizFourChoices(QuizFourChoices quizFourChoices) {
		repository.save(quizFourChoices);
	}

	@Override
	public void deleteQuizFourChoices(Integer id) {
		repository.deleteById(id);

	}
	
	public int insertFromCsv(MultipartFile file) throws Exception {
		List<Quiz> quizList = new ArrayList<>();
		/*処理次回記載*/
	}

}
