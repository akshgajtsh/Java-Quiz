package quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import quiz.entity.Quiz;
import quiz.entity.QuizFourChoices;
import quiz.repository.QuizFourChoicesRepository;
import quiz.service.QuizService;

@SpringBootApplication
public class QuizApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}
	
	@Autowired
	QuizService quizService;
	@Autowired
	QuizFourChoicesRepository repository;
	
	private void execute() {
//		setup();
//		showList();
//		showOne();
//		updateQuiz();
//		deleteQuiz();
		doQuiz();
	}
	
	private void setup() {
		System.out.println("----登録開始----");
		Quiz quiz1 = new Quiz(null, "「Java」はオブジェクト指向言語である", true, "登録太郎"); 
		
		Quiz quiz2 = new Quiz(null, "「Spring DATA」はデータアクセスに対する" + "機能を提供する", true, "登録太郎");
		
		Quiz quiz3 = new Quiz(null, "プログラムが沢山配置されているサーバーの" + "ことを「ライブラリ」という。", false, "登録太郎");
   
		Quiz quiz4 = new Quiz(null, "「@Component」はインスタンス生成アノテーション" + "である。", true, "登録太郎");

		Quiz quiz5 = new Quiz(null, "「Spring MVC」が実装している「デザインパターン」で" + "全てのリクエストを１つのコントローラーで受け取るパターンは" + "「シングルコントローラー・パターン」である。", false, "登録太郎");

		List<Quiz>quizList = new ArrayList();
		
		Collections.addAll(quizList, quiz1, quiz2, quiz3, quiz4, quiz5);
		
		for(Quiz quiz: quizList) {
			quizService.insertQuiz(quiz);
		}
	}
	
	private void showList() {
		System.out.println("----全件取得開始----");
		Iterable<Quiz> quizzes= quizService.selectAll();
		for(Quiz quiz: quizzes) {
			System.out.println(quiz);
		}
		System.out.println("----全件取得完了----");
	}
	
	private void showOne() {
		System.out.println("----1件取得開始----");
		Optional<Quiz> quizOpt= quizService.selectOneById(1);
		
		if(quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		}else {
			System.out.println("該当する問題がありません…");
		}
		System.out.println("----1件取得完了----");
	}
	
	private void updateQuiz() {
		System.out.println("----更新開始----");
		Quiz quiz1 = new Quiz(1, "「Spring」はフレームワークですか？", true, "変更タロウ");
		quizService.updateQuiz(quiz1);
		System.out.println("----更新終了----");
	}
	
	private void deleteQuiz() {
		System.out.println("----削除開始----");
		quizService.deleteQuizById(2);
		System.out.println("----削除終了----");
	}
	
	private void doQuiz() {
		System.out.println("----クイズ1件取得開始----");
		Optional<Quiz>quizOpt = quizService.selectOneRandom();
		
		if(quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		}else {
			System.out.println("該当する問題がありません");
		}
		System.out.println("----クイズ1件取得完了----");
		
		Boolean myAnswer = false;
		Integer id = quizOpt.get().getId();
		if(quizService.checkQuiz(id, myAnswer)) {
			System.out.println("正解です！");
		}else {
			System.out.println("不正解です…");
		}
		
	}
	
	@Override
	public void run(String... args) throws Exception{
		System.out.println("--- 🚀 4択クイズのテストデータ登録を開始します ---");

        // 1. テストデータのインスタンスを作成 (IDは自動採番されるので null)
        QuizFourChoices testQuiz = new QuizFourChoices(
            null,
            "Javaでボイラープレートコードを削減するためのライブラリはどれ？",
            "Spring Boot",
            "Lombok",
            "Hibernate",
            "PostgreSQL",
            2 // 正解は 2 (Lombok)
        );

        // 2. データベースに保存
        QuizFourChoices savedQuiz = repository.save(testQuiz);
        System.out.println("💾 データベースに保存しました！ ID: " + savedQuiz.getId());

        // 3. 全件取得してコンソールに表示
        System.out.println("🔍 登録されたデータを取得します：");
        repository.findAll().forEach(quiz -> {
            System.out.println("問題: " + quiz.getQuestion());
            System.out.println("選択肢1: " + quiz.getChoice_1());
            System.out.println("選択肢2: " + quiz.getChoice_2());
            System.out.println("正解番号: " + quiz.getAnswer());
        });

        System.out.println("--- 🎉 テストデータ登録テストが正常に終了しました ---");
	}

}
