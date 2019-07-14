# Hibernate Mapping Tutorial with Hibernate 5

- This project includes the tutorials for hibernate mapping such as OneToOne, OneToMany, ManyToMany
- I used Postgresql as a database and Fedora 30 as an operating system


## OneToOne Mapping

- In this scheme, there is a Instructor object and each Instructor has only one associated object called InstructorDetail
- Thus, there is a OneToOne relationship between Instructor and InstructorDetails

> Before running the project, please create the tables first.

- Open the terminal and write `sudo -u postgres psql` 
- Then choose your database via `\c yourDatabaseName` 
- Then write sql codes sequentially

**INSTRUCTOR DETAIL table** sql code:

```sql
CREATE TABLE INSTRUCTOR_DETAIL(
    id SERIAL PRIMARY KEY,
    youtube_channel TEXT,
    hobby TEXT
);
```



**INSTRUCTOR table** sql code:

```sql
CREATE TABLE INSTRUCTOR(
    id SERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT,
    instructor_detail_id integer,
    FOREIGN KEY (instructor_detail_id) REFERENCES INSTRUCTOR_DETAIL(id)
);
```

## OneToOne Bidirectional Mapping
- In the first example (OneToOne Mapping), I couldn't reach the `instructor` from the `instructor_detail` table. Now I will update only the Java codes (without touching the sql codes) which allow me to access instructor from instructor_detail
- To enable this propert, I need to use `mappedBy` property in the `@OneToOne` annotation.
- Shortly `@OneToOne(mappedBy="instructorDetail")`
- In detail, mappedBy means that:
	- The @JoinColumn annotation helps us specify the column we’ll use for joining an entity association or element collection. On the other hand, the mappedBy attribute is used to define the referencing side (non-owning side) of the relationship.
	- The value of mappedBy is the name of the association-mapping attribute on the owning side
	- When I apply this property in my project, I update the `InstructerDetail` class:

```java
public class SimpleInstructorDetail{
....
//add new property for bidirectional
	@OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
	private SimpleInstructor simpleInstructor;
	
	...
	
	public SimpleInstructor getSimpleInstructor() {
		return simpleInstructor;
	}


	public void setSimpleInstructor(SimpleInstructor simpleInstructor) {
		this.simpleInstructor = simpleInstructor;
	}
}
```

## OneToMany Mapping Bidirectional
- Instructor can have many course

> For simplicity, course can only be given one instructor

 - If I delete any instructor, I shouldn't delete the course !!
 - If I delete any course, I shouldn't delete the instructor of that course !!

> Before running the project, please add the new table called COURSE

```sql
CREATE TABLE COURSE(
    id SERIAL PRIMARY KEY,
    title TEXT UNIQUE,
    instructor_id integer,
    FOREIGN KEY (instructor_id) REFERENCES INSTRUCTOR(id)
);
```

> UNIQUE constraint is used to prevent duplicate course titles

## EAGER vs LAZY LOADING
- **Eager** loading will load all dependent entities
- For example, Instructor will have many courses. With eager loading we will load all the courses of the specific instructor
- **Lazy** loading will load only on request. It will only load the main entity first.
- For example, there are many students in the course, therefore we shouldn't load all the student when we load only one course.
- We can define eager or lazy load when we define the mapping relationship.
- Default type:
	- `@OneToOne : FetchType.EAGER`
	- `@OneToMany: FetchType.LAZY`
	- `@ManyToOne: FetchType.EAGER`
	- `@ManyToMany: FetchType.LAZY` 

```java
@Entity
@Table(name="instructor")
public class Instructor{
	...
	@OneToMany(fetch=FetchType.LAZY, mappedBy="instructor)
	private List<Course> courses;

}
```

- Because we are using lazy loading, we require an open Hibernate session
- What happens if the Hibernate session is closed?
	- If we trying to retrieve data, when session is closed, Hibernate will throw an exception
- **To retrieve data, we need an open Hibernate session**
- We have 2 options for retrieving lazy data:</br>
	1. `session.get` and call appropriate getter methods </br>
	2 Hibernate query with HQL
	
### 1. Call appropriate getter method

```java
public class MainApp{
	public static void main ... {
		Session session = factory.getCurrentSession();
		//...
		Instructor tempInstructor = session.get(Instructor.class, instructorId);
		
		System.out.println("Instructor" + tempInstructor);
		// this will hit the database on lazy loading
		System.out.println("Couses of that instructor " + tempInstructor.getCourses);
		// close the session
		session.close();
		// because of the data in the memory, this will not hit the database, and print the courses correctly
		System.out.println("From memory " + tempInstructor.getCourses());
	}
}
```

### 2. Hibernate Query with HQL

```java
public class MainApp{
	public static void main ... {
		Session session = factory.getCurrentSession();
		//...
		Long instructorId = 1;
		Query<Instructor> query = session.createQuery("select i from Instructor i ",
											+"JOIN FETCH i.courses "
											+"where i.id =:instructorId", 
												Instructor.class);
		query.setParameter("instructorId", instructorId);
		
		// execute query and get instructor
		// this will load all courses at once, because of the query
		Instructor tempInstructor = query.getSingleResult();
		System.out.println("Instructor" + tempInstructor);
	
		session.getTransaction().commit();
		session.close();
		
		System.out.println("This courses come from memory:" + tempInstructor.getCourses());
	}
}
```

## OneToMany Uni-Directional
- A courses can have many reviews
- If I delete a course, I should also delete the reviews
- Reviews without a course, have no meaning

> Before running the project add the REVIEW table to the database

```sql
CREATE TABLE REVIEW(
    id SERIAL PRIMARY KEY,
    comment TEXT,
    course_id integer,
    FOREIGN KEY (course_id) REFERENCES COURSE(id)
);
```


## ManyToMany Relation
- Courses can have many students
- Students can have many courses

First we need to track which student is in which course course and vice-versa

To do that, we will add 2 tables (one for Student and one for CourseStudent). The latter one will hold the foreign keys for each table called `Join Table`

- Student table:

```sql
CREATE TABLE STUDENT(
	id SERIAL PRIMARY KEY,
	first_name TEXT,
	last_name TEXT,
	email TEXT
);
```

- CourseStudent table

```sql
CREATE TABLE COURSE_STUDENT(
	course_id integer,
	student_id integer,
	PRIMARY KEY(course_id, student_id),
	FOREIGN KEY (course_id) REFERENCES COURSE(id),
	FOREIGN KEY (student_id) REFERENCES STUDENT(id)
);
```
	
	

For example:

- Course table includes:

10, Java, 2 (CouseId, Title, InstructorId)</br>
20, Python, 2 (CouseId, Title, InstructorId)

- and Student table includes

100, John, Doe, test@test.com (StudentId, FirstName, LastName, Email)</br>
200, Susan, Dash, test1@test.com (StudentId, FirstName, LastName, Email)

- then, CourseStudent table will include something like that :

10, 100 (CourseId, StudentId)</br>
10, 200 (CourseId, StudentId)</br>
20, 200 (CourseId, StudentId)


- Create the Student java class which includes code segment like:

```java
public class Student{
	//...
	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH}
			)
	@JoinTable(
			name = "COURSE_STUDENT",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id")
			)
	private List<Course> courses;
}
```

- Now update the Course class like this:

```java
public class Course{
	...
	@ManyToMany
	@JoinTable(
		name = "COURSE_STUDENT"
		joinColumns = @JoinColumn(name = "course_id"), 
		inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	private List<Students> students;
}
```



