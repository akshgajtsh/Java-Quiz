package quiz.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quiz.entity.QuizFourChoices;
import quiz.service.QuizFourChoicesService;

@Controller
@RequestMapping("/quizFourChoices")
public class QuizFourChoicesPlayController {
	@Autowired
	private QuizFourChoicesService service;

	@GetMapping("/play")
	public String play(Model model) {
		Optional<QuizFourChoices> quizOpt = service.selectOneRandom();

		if (quizOpt.isPresent()) {
			model.addAttribute("quiz", quizOpt.get());
		} else {
			model.addAttribute("mag", "まだクイズが登録されていません");
		}
		return "fourquizplay";
	}

	@PostMapping("/check")
	public String check(@RequestParam Integer id, @RequestParam Integer userAnswer, Model model) {
		boolean correct = service.checkAnswer(id, userAnswer);

		model.addAttribute("correct", correct);
		Optional<QuizFourChoices> quizOpt = service.selectOneById(id);
		if (quizOpt.isPresent()) {
			QuizFourChoices quiz = quizOpt.get();
			model.addAttribute("quiz", quiz);
			if (correct) {
				model.addAttribute("result", "✨ 正解！お見事！");
				model.addAttribute("boxClass", "alert-success");
				model.addAttribute("iscorrect", true);
			} else {
				model.addAttribute("result", "❌ 残念！不正解！");
				model.addAttribute("boxClass", "alert-danger");
				model.addAttribute("iscorrect", false);

				String rightAnswer = "";
				if (quiz.getAnswer() == 1)
					rightAnswer = quiz.getChoice_1();
				if (quiz.getAnswer() == 2)
					rightAnswer = quiz.getChoice_2();
				if (quiz.getAnswer() == 3)
					rightAnswer = quiz.getChoice_3();
				if (quiz.getAnswer() == 4)
					rightAnswer = quiz.getChoice_4();
				model.addAttribute("rightAnswer", rightAnswer);
			}
		}
		;

		service.selectOneById(id).ifPresent(quiz -> model.addAttribute("quiz", quiz));
		return "fourquizplay";
	}

}
