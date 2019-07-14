package com.hibernateTutorial.manyToMany;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {

	private static final String HIBERNATE_CONFIG_PATH = "/home/mehmetozanguven/eclipse-workspace/HibernateTutorial/src/main/resources/hibernate-cfg.xml";

	public static void main(String[] args) {

		Configuration hibernateConf = new Configuration().configure(new File(HIBERNATE_CONFIG_PATH))
				.addAnnotatedClass(SimpleInstructor.class)
				.addAnnotatedClass(SimpleInstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.addAnnotatedClass(Student.class);

		SessionFactory factory = hibernateConf.buildSessionFactory();

		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			addStudentToCourse(session);
			

		} finally {
			session.close();
			factory.close();
		}
	}

	private static void addStudentToCourse(Session session) {
		Course mathCourse = getCourseById(session, (long) 2);
		Student s1 = new Student("test", "test", "test@test.com");
		Student s2 = new Student("test1", "test1", "test1@test1.com");
		mathCourse.addStudentToCourse(s1);
		mathCourse.addStudentToCourse(s2);
		session.save(s1);
		session.save(s2);
		session.getTransaction().commit();
	}
	
	private static void getReviewForCourse(Session session, Long courseId) {
		Course tempCourse = session.get(Course.class, courseId);
		
		if (tempCourse == null){
			System.out.println("There is no course with these id: " + courseId);
		}else {
			tempCourse.getAllReviews().forEach(elem -> System.out.println(elem));
			session.close();

		}
		
	}
	
	private static void addReviewToSpecificCourse(Session session, Course course) {
		Review r1 = new Review("So so good");
		Review r2 = new Review("Not bad");
		
		course.addReviewToCourse(r1);
		course.addReviewToCourse(r2);
		
		session.save(course);
		
		session.getTransaction().commit();
	}

	private static Course getCourseById(Session session, Long courseId) {
		Course tempCourse = session.get(Course.class, courseId);
		
		if (tempCourse == null){
			System.out.println("There is no course with these id: " + courseId);
			throw new NullPointerException();
		}else {
			return tempCourse;
		}
		
	}
	
	private static void addTwoCoursesToOneInstructor(Session session, Long instructorId) {

		SimpleInstructor tempInstructor = session.get(SimpleInstructor.class, instructorId);

		if (tempInstructor == null)
			System.out.println("There is no instructor with these id: " + instructorId);
		else {
			Course mathCourse = new Course("Math");
			Course javaCourse = new Course("Java");

			tempInstructor.addCourse(javaCourse);
			tempInstructor.addCourse(mathCourse);
			
			session.save(mathCourse);
			session.save(javaCourse);
			session.getTransaction().commit();
		}
	}

	private static void getInstructorCourses(Session session, Long instructorId) {

		SimpleInstructor tempInstructor = session.get(SimpleInstructor.class, instructorId);

		if (tempInstructor == null)
			System.out.println("There is no instructor with these id: " + instructorId);
		else {
			System.out.println(tempInstructor.getCourses());
			System.out.println(tempInstructor.getInstructorDetail());
		}
	}
	
	

}
