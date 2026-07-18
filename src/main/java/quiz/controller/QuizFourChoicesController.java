package quiz.controller;

import java.util.Optional;

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

import quiz.entity.QuizFourChoices;
import quiz.form.QuizFourChoicesForm;
import quiz.service.QuizFourChoicesService;

@Controller
@RequestMapping("/quizFourChoices")
public class QuizFourChoicesController {

	@Autowired
	private QuizFourChoicesService service;

	@ModelAttribute
	public QuizFourChoicesForm setUpForm() {
		QuizFourChoicesForm form = new QuizFourChoicesForm();
		return form;
	}

	@GetMapping
	public String showList(QuizFourChoicesForm quizFourChoicesForm, Model model) {
		quizFourChoicesForm.setNewFourQuiz(true);

		Iterable<QuizFourChoices> list = service.selectAll();
		model.addAttribute("list", list);
		model.addAttribute("title", "4択クイズ一覧");
		return "fourquizlist";
	}

	@PostMapping("/insert")
	public String insert(@Validated QuizFourChoicesForm quizFourChoicesForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		QuizFourChoices quiz = new QuizFourChoices();
		quiz.setQuestion(quizFourChoicesForm.getQuestion());
		quiz.setChoice_1(quizFourChoicesForm.getChoice_1());
		quiz.setChoice_2(quizFourChoicesForm.getChoice_2());
		quiz.setChoice_3(quizFourChoicesForm.getChoice_3());
		quiz.setChoice_4(quizFourChoicesForm.getChoice_4());
		quiz.setAnswer(quizFourChoicesForm.getAnswer());
		quiz.setAuthor(quizFourChoicesForm.getAuthor());

		if (bindingResult.hasErrors()) {
			return showList(quizFourChoicesForm, model);
		}

		service.insertQuizFourChoices(quiz);
		redirectAttributes.addFlashAttribute("complete", "登録が完了しました!");
		return "redirect:/quizFourChoices";
	}

	@GetMapping("/edit/{id}")
	public String showEdit(QuizFourChoicesForm quizFourChoicesForm, @PathVariable Integer id, Model model) {
		Optional<QuizFourChoices> quizOpt = service.selectOneById(id);

		if (quizOpt.isPresent()) {
			QuizFourChoices quiz = quizOpt.get();

			quizFourChoicesForm.setQuestion(quiz.getQuestion());
			quizFourChoicesForm.setChoice_1(quiz.getChoice_1());
			quizFourChoicesForm.setChoice_2(quiz.getChoice_2());
			quizFourChoicesForm.setChoice_3(quiz.getChoice_3());
			quizFourChoicesForm.setChoice_4(quiz.getChoice_4());
			quizFourChoicesForm.setAnswer(quiz.getAnswer());
			quizFourChoicesForm.setAuthor(quiz.getAuthor());

			quizFourChoicesForm.setNewFourQuiz(false);

			model.addAttribute("quizFourChoicesForm", quizFourChoicesForm);
		}

		Iterable<QuizFourChoices> list = service.selectAll();
		model.addAttribute("list", list);
		model.addAttribute("title", "4択クイズ更新用フォーム");
		return "fourquizlist";

	}

	@PostMapping("/update")
	public String update(@Validated QuizFourChoicesForm quizFourChoicesForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			quizFourChoicesForm.setNewFourQuiz(false);
			return showList(quizFourChoicesForm, model);
		}

		QuizFourChoices quiz = new QuizFourChoices();
		quiz.setId(quizFourChoicesForm.getId());
		quiz.setQuestion(quizFourChoicesForm.getQuestion());
		quiz.setChoice_1(quizFourChoicesForm.getChoice_1());
		quiz.setChoice_2(quizFourChoicesForm.getChoice_2());
		quiz.setChoice_3(quizFourChoicesForm.getChoice_3());
		quiz.setChoice_4(quizFourChoicesForm.getChoice_4());
		quiz.setAnswer(quizFourChoicesForm.getAnswer());
		quiz.setAuthor(quizFourChoicesForm.getAuthor());

		service.updateQuizFourChoices(quiz);
		redirectAttributes.addFlashAttribute("complete", "更新が完了しました!");
		return "redirect:/quizFourChoices";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		service.deleteQuizFourChoices(id);
		return "redirect:/quizFourChoices";
	}
}
