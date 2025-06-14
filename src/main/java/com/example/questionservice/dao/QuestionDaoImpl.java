package com.example.questionservice.dao;

import org.springframework.stereotype.Repository;

import com.example.questionservice.model.Question;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDaoCustom{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Integer> findRandomQuestionsByCategory(String category, int numQ) {
        String sql = "SELECT id FROM question WHERE category = :category ORDER BY RAND()";
        Query query = entityManager.createNativeQuery(sql, Question.class);
        query.setParameter("category", category);
        query.setMaxResults(numQ);
        return query.getResultList();
    }

}
