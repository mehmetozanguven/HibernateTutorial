package com.hibernateTutorial.eagerVsLazy;

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
				.addAnnotatedClass(Course.class);

		SessionFactory factory = hibernateConf.buildSessionFactory();

		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();

			//addTwoCoursesToOneInstructor(session, (long)2);
			getInstructorCourses(session, (long)2);
			//removeCourse(session, (long)1);

		} finally {
			session.close();
			factory.close();
		}
	}

	private static void addTwoCoursesToOneInstructor(Session session, Long instructorId) {
		session.beginTransaction();

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
	
	private static void removeCourse(Session session, Long courseId) {
		
		Course tempCourse = session.get(Course.class, courseId);
		
		if	(tempCourse == null) {
			System.out.println("There is no course with these id: " + courseId);
		}else {
			session.delete(tempCourse);
			session.getTransaction().commit();
		}
	}


}
