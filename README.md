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
    instructor_detail_id SERIAL,
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
	
	 

