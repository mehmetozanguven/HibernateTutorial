package com.hibernateTutorial.oneToManyRelation;

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

			//addTwoCoursesToOneInstructor(session, (long)2);
			getInstructorCourses(session, (long)2);

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
		session.beginTransaction();

		SimpleInstructor tempInstructor = session.get(SimpleInstructor.class, instructorId);

		if (tempInstructor == null)
			System.out.println("There is no instructor with these id: " + instructorId);
		else {
			System.out.println(tempInstructor.getCourses());
			System.out.println(tempInstructor.getInstructorDetail());
		}
	}


}
