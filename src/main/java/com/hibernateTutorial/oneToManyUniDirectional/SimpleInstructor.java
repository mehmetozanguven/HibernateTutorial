package com.hibernateTutorial.oneToManyUniDirectional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INSTRUCTOR")
public class SimpleInstructor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	// set up mapping to InstructorDetail entity
	// CascadeType.ALL means that all operation done
	// in Instructor will be done also for InstructorDetail
	// for example: if we delete instructor from the database
	// it will also delete associate instructor detail from the database
	// Then, we specify how to map these entities
	// via the @JoinColumn.
	// 'instructor_detail_id' is the references that we wrote in the sql code
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "instructor_detail_id")
	private SimpleInstructorDetail instructorDetail;
	
	// setting up bidirectional mapping for Instructor and Course
	// Course is the owner side, Instructor is the referencing side
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			mappedBy = "simpleInstructor" )
	private List<Course> courses;
	
	public SimpleInstructor() {
		
	}


	public SimpleInstructor(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public SimpleInstructorDetail getInstructorDetail() {
		return instructorDetail;
	}


	public void setInstructorDetail(SimpleInstructorDetail instructorDetail) {
		this.instructorDetail = instructorDetail;
	}


	public List<Course> getCourses() {
		return courses;
	}


	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	// add convenience method for bi-directional relationship
	public void addCourse(Course course) {
		if (courses == null)
			courses = new ArrayList<Course>();
		
		courses.add(course);
		course.setSimpleInstructor(this);
	}

	
	
}

