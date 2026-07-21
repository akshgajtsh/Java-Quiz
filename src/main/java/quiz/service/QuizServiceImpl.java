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
		if (randId == null) {
			return Optional.empty();
		}
		return quizRepository.findById(randId);
	}

	@Override
	public Boolean checkQuiz(Integer id, Boolean myAnswer) {
		// TODO 自動生成されたメソッド・スタブ
		Boolean check = false;
		Optional<Quiz> optQuiz = quizRepository.findById(id);
		if (optQuiz.isPresent()) {
			Quiz quiz = optQuiz.get();

			if (quiz.getAnswer().equals(myAnswer)) {
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

	@Override
	public List<Quiz> select10Random() {
		return quizRepository.getRandomQuiz();
	}

	public int insertFromCsv(MultipartFile file) throws Exception {
		List<Quiz> quizList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
             String line;
             while((line = reader.readLine()) != null) {
            	 if(line.trim().isEmpty()) continue;
            	 
            	 String[] data = line.split(",");
            	 if(data.length < 2) continue;
            	 
            	 Quiz quiz = new Quiz();
            	 quiz.setQuestion(data[0].trim());
            	 quiz.setAnswer(Boolean.parseBoolean(data[1].trim()));
            	 quiz.setAuthor("CSVインポート");
            	 
            	 quizList.add(quiz);
             }
             
             if(!quizList.isEmpty()) {
            	 quizRepository.saveAll(quizList);
             }
             
             return quizList.size();
		}

	}

}
