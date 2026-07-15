package quiz.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("quiz_four_choices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizFourChoices {
	@Id
    private Integer id;
    private String question;
    private String choice_1;
    private String choice_2;
    private String choice_3;
    private String choice_4;
    private Integer answer;

}
