package quiz.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		if (optQuiz.isPresent()) {
			QuizFourChoices quizFourChoices = optQuiz.get();
			if (quizFourChoices.getAnswer().equals(userAnswer)) {
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
		/*処理次回記載*/
		List<QuizFourChoices> quizList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;

				String[] data = line.split(",");
				if (data.length < 6)
					continue;

				QuizFourChoices quiz = new QuizFourChoices();
				quiz.setQuestion(data[0].trim());
				quiz.setChoice_1(data[1].trim());
				quiz.setChoice_2(data[2].trim());
				quiz.setChoice_3(data[3].trim());
				quiz.setChoice_4(data[4].trim());
				quiz.setAnswer(Integer.parseInt(data[5].trim()));

				quizList.add(quiz);
			}

			if (!quizList.isEmpty()) {
				repository.saveAll(quizList);
			}

			return quizList.size();
		}
	}

}
