package quiz.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import quiz.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
public class QuizApiController {
	@Autowired
	private QuizService quizService;
	
	@PostMapping("/upload-csv")
	public ResponseEntity<Map<String, Object>> uploadCsv(@RequestParam("file") MultipartFile file){
		Map<String, Object> response = new HashMap();
		
		if(file.isEmpty()) {
			response.put("success", false);
			response.put("message", "ファイルを選択してください");
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			int count = quizService.insertFromCsv(file);
			response.put("success", true);
			response.put("message", count + "件のクイズ一括登録しました");
			response.put("count", count);
			return ResponseEntity.ok(response);
		} catch(Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("message", "CSVの読み込みに失敗しました");
			return ResponseEntity.internalServerError().body(response);
		}
	}

}
