package com.example.questionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import com.example.questionservice.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    
    //LoadBalancers checking....
    @Autowired
    Environment environment;

    @GetMapping("getAll")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    
    @GetMapping("get/{id}")
    public ResponseEntity<Question> getQuestionBasedOnId(@PathVariable Integer id){
        return questionService.getQuestionBasedOnId(id);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id){
        return  questionService.deleteQuestion(id);
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion) {
    	return questionService.updateQuestion(id, updatedQuestion);
    }
    
    /*Things Question Service has to do....- works once quiz service exists 
     * Generate Questions for quiz and return only Id's of Questions  */
    
    
     /*GET http://localhost:8081/question/createQuizQuestions?category=sql&numQ=2*/
    @GetMapping("createQuizQuestions")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz
    				(@RequestParam String category,@RequestParam Integer numQ) {
    	
    	return questionService.generateQuestionsForQuiz(category, numQ);
    }
    
    
    /*POST http://localhost:8081/question/getQuizQuestions
     * [1,7,8,9]*/
    @PostMapping("/getQuizQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionIds){
    	System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionFromId(questionIds);
    }
    
    
    /* POST http://localhost:8081/question/getScore
     * [
    {
        "id": 1,
        "response": "1995"
    },
    {
        "id": 7,
        "response": "Method Overriding"
    },
    {
        "id": 8,
        "response":  "Abstract class"
    },
    {
        "id": 9,
        "response": "ArrayIndexOutOfBoundsException"
    }
]*/
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		return questionService.getScore(responses);
    }
}
