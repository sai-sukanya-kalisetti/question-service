package com.example.questionservice.model;

/**
 * Request parameters to send for quiz
 */
public class QuizDTO {
	private String category;
	private int numOfQuestions;
	private String title;
	@Override
	public String toString() {
		return "QuizDTO [category=" + category + ", numOfQuestions=" + numOfQuestions + ", title=" + title + "]";
	}
	public QuizDTO(String category, int numOfQuestions, String title) {
		super();
		this.category = category;
		this.numOfQuestions = numOfQuestions;
		this.title = title;
	}
	public QuizDTO() {
		super();
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getNumOfQuestions() {
		return numOfQuestions;
	}
	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
