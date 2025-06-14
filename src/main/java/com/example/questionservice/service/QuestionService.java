package com.example.questionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.questionservice.dao.QuestionDao;
import com.example.questionservice.dao.QuestionDaoCustom;
import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;


@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    
    @Autowired
    QuestionDaoCustom questionDaoCustom;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    /*Using Optional class to avoid Null Pointer Exception*/
    public ResponseEntity<Question> getQuestionBasedOnId(Integer id) {
        Optional<Question> optionalQuestion = questionDao.findById(id);
        
        if(optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*Using lambda - map */
	public ResponseEntity<String> deleteQuestion(Integer id) {
		 return questionDao.findById(id)
		            .map(question -> {
		                questionDao.deleteById(id);
		                return ResponseEntity.ok("Deleted");
		            })
		            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id Not Found"));
	}

	public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion) {
		return questionDao.findById(id)
                .map(existingQuestion -> {
                    // Update all fields
                    existingQuestion.setCategory(updatedQuestion.getCategory());
                    existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
                    existingQuestion.setOption1(updatedQuestion.getOption1());
                    existingQuestion.setOption2(updatedQuestion.getOption2());
                    existingQuestion.setOption3(updatedQuestion.getOption3());
                    existingQuestion.setOption4(updatedQuestion.getOption4());
                    existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
                    existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());

                    questionDao.save(existingQuestion);
                    return ResponseEntity.ok("Question updated successfully");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question ID not found"));
	}
	
	/*Things Question Service has to do....*/
    //Generate Questions for quiz and return only Id's of Questions - works once quiz service exists 

	public ResponseEntity<List<Integer>> generateQuestionsForQuiz(String category, Integer numQ) {
		List<Integer> questionIds = questionDaoCustom.findRandomQuestionsByCategory(category, numQ);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(questionIds);
	}
	
	//Get Quiz Questions based on Id's
	//from Id's - get qs - add in list of questions 
	//Now -  create wrapper - add in wrappers
	public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
		List<QuestionWrapper> wrappersList = new ArrayList<>();
		List<Question> questionsList = new ArrayList<>();
		for(Integer id: questionIds)
		{
			questionsList.add(questionDao.findById(id).get());
		}
		for(Question question : questionsList)
		{
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrappersList.add(wrapper);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(wrappersList);
	}

	//directly compare response answer with question right answer
	public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score = 0;
        for(Response response : responses){
        	Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
            	score++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
	}

}
