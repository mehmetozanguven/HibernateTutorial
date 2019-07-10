package com.hibernateTutorial.oneToOneRelation;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
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
        	
        	saveEmployee(session);
        	//deleteEmployee(session, (long) 1);
        	
        }finally {
			session.close();
		}
    }

	private static void deleteEmployee(Session session, Long instructorId) {
		session.beginTransaction();
		
		//get the instructor by id;
		// this will return null if not found
		SimpleInstructor tempInstructor = session.get(SimpleInstructor.class, instructorId);
		
		if(tempInstructor == null)
			System.out.println("There is no data with these id: " + instructorId);
		else {
			System.out.println("Deleting: " + tempInstructor);
			session.delete(tempInstructor);
		}
		
		session.getTransaction().commit();
	
	}

	private static void saveEmployee(Session session) {
		SimpleInstructor tempInstructor = new SimpleInstructor("Ozan", "Guven", "test@test.com");
		SimpleInstructorDetail tempInstuctorDetail = new SimpleInstructorDetail("http://www.test.com", "Football");
		tempInstructor.setInstructorDetail(tempInstuctorDetail);
		
		session.beginTransaction();
		
		// save the instructor
		// this will also save the details object
		// because of CascadeType.All
		session.save(tempInstructor);
		
		session.getTransaction().commit();
	}
}
