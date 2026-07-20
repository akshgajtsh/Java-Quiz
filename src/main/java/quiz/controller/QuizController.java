package quiz.controller;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import quiz.entity.Quiz;
import quiz.form.QuizForm;
import quiz.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {
	@Autowired
	private QuizService quizService;

	@ModelAttribute
	public QuizForm setUpForm() {
		QuizForm form = new QuizForm();

		form.setAnswer(true);
		return form;
	}

	@GetMapping
	public String showList(QuizForm quizForm, Model model) {
		quizForm.setNewQuiz(true);

		Iterable<Quiz> list = quizService.selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "crud";
	}

	@PostMapping("/insert")
	public String insert(@Validated QuizForm quizForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		Quiz quiz = new Quiz();

		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());

		if (!bindingResult.hasErrors()) {
			quizService.insertQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました");
			return "redirect:/quiz";
		} else {
			return showList(quizForm, model);
		}

	}

	@GetMapping("/{id}")
	public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
		Optional<Quiz> quizOpt = quizService.selectOneById(id);
		Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));

		if (quizFormOpt.isPresent()) {
			quizForm = quizFormOpt.get();
		}

		makeUpdateModel(quizForm, model);
		return "crud";
	}

	private void makeUpdateModel(QuizForm quizForm, Model model) {
		model.addAttribute("id", quizForm.getId());
		quizForm.setNewQuiz(false);
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("title", "更新用フォーム");
	}

	@PostMapping("/update")
	public String update(@Validated QuizForm quizForm, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		Quiz quiz = makeQuiz(quizForm);
		if (!result.hasErrors()) {
			quizService.updateQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました");
			//			return "redirect:/quiz/" + quiz.getId();
			return "redirect:/quiz";
		} else {
			makeUpdateModel(quizForm, model);
			return "crud";
		}
	}

	private Quiz makeQuiz(QuizForm quizForm) {
		Quiz quiz = new Quiz();
		quiz.setId(quizForm.getId());
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		return quiz;
	}

	private QuizForm makeQuizForm(Quiz quiz) {
		QuizForm form = new QuizForm();
		form.setId(quiz.getId());
		form.setQuestion(quiz.getQuestion());
		form.setAnswer(form.getAnswer());
		form.setAuthor(quiz.getAuthor());
		form.setNewQuiz(false);
		return form;
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
		quizService.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("decomplete", "削除が完了しました");
		return "redirect:/quiz";
	}

	@GetMapping("/play")
	public String showQuiz(HttpSession session, Model model) {
		List<Quiz> quizzes = (List<Quiz>) session.getAttribute("quizzes");
		Integer currentIndex = (Integer) session.getAttribute("currentIndex");

		if (quizzes == null || currentIndex == null) {
			quizzes = quizService.select10Random();
			if (quizzes.isEmpty()) {
				model.addAttribute("msg", "問題がありません");
				return "play";
			}

			currentIndex = 0;
			session.setAttribute("quizzes", quizzes);
			session.setAttribute("currentIndex", currentIndex);
			session.setAttribute("score", 0);

		}
		Quiz currentQuiz = quizzes.get(currentIndex);
		QuizForm quizForm = makeQuizForm(currentQuiz);
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("currentNumber", currentIndex + 1);
		return "play";
	}

	@PostMapping("/check")
	public String checkQuiz(@RequestParam Boolean answer, HttpSession session, Model model) {
		List<Quiz> quizzes = (List<Quiz>) session.getAttribute("quizzes");
		Integer currentIndex = (Integer) session.getAttribute("currentIndex");
		Integer score = (Integer) session.getAttribute("score");

		if (quizzes == null || currentIndex == null) {
			return "redirect:/quiz/play";
		}
		Quiz currentQuiz = quizzes.get(currentIndex);
		if (currentQuiz.getAnswer().equals(answer)) {
           score++;
           session.setAttribute("score", score);
		}
		currentIndex++;
		session.setAttribute("currentIndex", currentIndex);
		
		if(currentIndex >= 10 || currentIndex >= quizzes.size()) {
			model.addAttribute("score",score);
			
			session.removeAttribute("quizzes");
			session.removeAttribute("currentIndex");
			session.removeAttribute("score");
			return "answer";
		}
		return "redirect:/quiz/play";
	}

}
