package com.learn.Question_service.ontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.Question_service.model.Question;
import com.learn.Question_service.model.QuestionWrapper;
import com.learn.Question_service.model.Response;
import com.learn.Question_service.service.QuestionService;

@RestController
public class QuestionController {

	@Autowired
	private QuestionService service;

	// Simple health check or welcome endpoint
	@GetMapping("show")
	public String show() {
		return "welcome";
	}

	// Endpoint to save multiple questions (bulk insert)
	@PostMapping("Question/saveAll")
	public ResponseEntity<List<Question>> saveAllQuestions(@RequestBody List<Question> questions) {
		try {
			List<Question> savedQuestions = service.saveAllQuestions(questions);
			return new ResponseEntity<>(savedQuestions, HttpStatus.CREATED); // Return created status
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors
																								// gracefully
		}
	}

	// Endpoint to save a single question
	@PostMapping("Question/save")
	public ResponseEntity<Question> saveQuestions(@RequestBody Question question) {
		try {
			Question savedQuestion = service.saveQuestion(question);
			return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED); // Return created status
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors gracefully
		}
	}

	// Endpoint to get all questions
	@GetMapping("Question/getAll")
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			List<Question> questions = service.getAllQuestions();
			if (questions.isEmpty()) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT); // Return 204 if no content
			}
			return new ResponseEntity<>(questions, HttpStatus.OK); // Return 200 OK if content exists
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors gracefully
		}
	}

	// Endpoint to get a single question by ID
	@GetMapping("Question/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
		Optional<Question> question = service.getQuestionById(id);
		if (question.isPresent()) {
			return new ResponseEntity<>(question.get(), HttpStatus.OK); // Return 200 OK if question found
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if question not found
		}
	}

	@GetMapping("getQuestionForQuiz")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQ) {
		List<Integer> questions = service.getQuestionsForQuiz(category, numQ);
		return new ResponseEntity<>((questions), HttpStatus.OK); // Return 200 OK if question found

	}

	@PostMapping("getQuestionsByIds")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(@RequestBody List<Integer> questionIds) {
		List<QuestionWrapper> questions = service.getQuestionsByIds(questionIds);
		return new ResponseEntity<>(questions, HttpStatus.OK); // Return 200 OK if question found

	}

	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> Responses) {
		Integer score = service.getScore(Responses);
		return new ResponseEntity<>(score, HttpStatus.OK); // Return 200 OK if question found

	}
}
