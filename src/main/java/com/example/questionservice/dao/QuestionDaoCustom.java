package com.example.questionservice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.questionservice.model.Question;

@Repository
public interface QuestionDaoCustom {
	
	   List<Integer> findRandomQuestionsByCategory(String category, int numQ);

}
