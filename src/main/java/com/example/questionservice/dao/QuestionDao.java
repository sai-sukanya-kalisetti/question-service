package com.example.questionservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionservice.model.Question;


@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
	
	List<Question> findByCategory(String category);
	
//	@Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RAND() LIMIT :numQ", 
//		    nativeQuery = true)
//		List<Question> findRandomQuestionsByCategory(String category,int numQ);
}
