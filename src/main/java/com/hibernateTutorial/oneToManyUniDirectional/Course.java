package com.hibernateTutorial.oneToManyUniDirectional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "instructor_id")
	private SimpleInstructor simpleInstructor;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Review> allReviews;
	
	public Course() {
		
	}

	public Course(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SimpleInstructor getSimpleInstructor() {
		return simpleInstructor;
	}

	public void setSimpleInstructor(SimpleInstructor simpleInstructor) {
		this.simpleInstructor = simpleInstructor;
	}

	public List<Review> getAllReviews() {
		return allReviews;
	}

	public void setAllReviews(List<Review> allReviews) {
		this.allReviews = allReviews;
	}
	
	// add a review to course
	public void addReviewToCourse(Review review) {
		if (allReviews == null)
			allReviews = new ArrayList<Review>();
		review.setCourse(this);
		allReviews.add(review);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", simpleInstructor=" + simpleInstructor + "]";
	}
	
	
	
}
