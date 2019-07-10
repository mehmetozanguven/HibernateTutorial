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

