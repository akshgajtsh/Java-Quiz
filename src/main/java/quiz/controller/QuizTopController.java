package quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizTopController {
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title", "Quizアプリケーション");
		return "top";
	}

}
