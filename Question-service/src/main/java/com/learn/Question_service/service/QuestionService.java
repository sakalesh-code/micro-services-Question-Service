package com.learn.Question_service.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.Question_service.QuestionDao.QuestionDao;
import com.learn.Question_service.model.Question;
import com.learn.Question_service.model.QuestionWrapper;
import com.learn.Question_service.model.Response;

@Service
public class QuestionService {

	@Autowired
	private QuestionDao qsdao;

	// Get all questions
	public List<Question> getAllQuestions() {
		return qsdao.findAll();
	}

	// Save all questions
	public List<Question> saveAllQuestions(List<Question> questions) {
		return qsdao.saveAll(questions);
	}

	// Save a single question
	public Question saveQuestion(Question question) {
		return qsdao.save(question);
	}

	// Get a question by ID
	public Optional<Question> getQuestionById(int id) {
		return qsdao.findById(id);
	}

	public List<Integer> getQuestionsForQuiz(String category, int numQ) {
		List<Integer> questions = qsdao.findQuestionsByCategory(category);
		Collections.shuffle(questions);
		// Get the first 'numQ' random questions
		List<Integer> selectedQuestions = questions.subList(0, Math.min(numQ, questions.size()));
		return selectedQuestions;
	}

	public List<QuestionWrapper> getQuestionsByIds(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		for (Integer id : questionIds) {
			Question question = qsdao.findById(id).get();
			questions.add(question);
		}
		for (Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrappers.add(wrapper);
			System.out.println("wrapper::"+wrapper);
		}
		return wrappers;
	}

	public Integer getScore(List<Response> responses) {

		int right = 0;
		for (Response response : responses) {
			Question question = qsdao.findById(response.getId()).get();
			if (response.getResponse().equals(question.getRightAnswer())) {
				right++;
			}
		}

		return right;
	}
}
