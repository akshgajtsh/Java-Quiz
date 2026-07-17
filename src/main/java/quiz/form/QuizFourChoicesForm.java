package quiz.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizFourChoicesForm {
	private Integer id;
	@NotBlank
	private String question;
	@NotBlank
	private String choice_1;
	@NotBlank
	private String choice_2;
	@NotBlank
	private String choice_3;
	@NotBlank
	private String choice_4;
	@NotNull
	private Integer answer;
	@NotBlank
	private String author;

	private Boolean newFourQuiz;

}
