package com.hibernateTutorial.oneToOneRelationBidirectional;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MainApp {

	private static final String HIBERNATE_CONFIG_PATH = "/home/mehmetozanguven/eclipse-workspace/HibernateTutorial/src/main/resources/hibernate-cfg.xml";
	public static void main( String[] args ){
		
		Configuration hibernateConf = new Configuration()
									.configure(new File(HIBERNATE_CONFIG_PATH))
									.addAnnotatedClass(SimpleInstructor.class)
									.addAnnotatedClass(SimpleInstructorDetail.class);
		
        SessionFactory factory = hibernateConf.buildSessionFactory();
        
        Session session = factory.getCurrentSession();
        
        try {
        	
        	findInstructorFromInstructorDetail(session, (long)2);
        	
        }finally {
			session.close();
		}
    }
	
	private static void findInstructorFromInstructorDetail(Session session, Long instructorDetailId) {
		session.beginTransaction();
		
		//get the instructor_detail by id
		SimpleInstructorDetail tempInstructorDetail = session.get(SimpleInstructorDetail.class, instructorDetailId);
		
		if (tempInstructorDetail == null)
			System.out.println("There is no data with these instructor_detail_id: " + instructorDetailId);
		else {
			System.out.println("Here is the instuctor detail object: " + tempInstructorDetail);
			System.out.println("Here is the instructor from instructor detail: " + tempInstructorDetail.getSimpleInstructor());
		}
	}
	
	

}
