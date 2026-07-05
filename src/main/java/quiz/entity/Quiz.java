package quiz.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
	@Id
	private Integer id;
	
//	クイズ内容
	private String question;
	
//	クイズ回答
	private Boolean answer;
	
//	クイズ作成者
	private String author;

}
