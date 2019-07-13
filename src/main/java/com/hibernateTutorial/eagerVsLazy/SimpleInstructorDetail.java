package com.hibernateTutorial.eagerVsLazy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INSTRUCTOR_DETAIL")
public class SimpleInstructorDetail{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "youtube_channel")
	private String youtubeChannel;
	
	@Column(name = "hobby")
	private String hobby;
	
	//add new property for bidirectional
	@OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
	private SimpleInstructor simpleInstructor;
	
	public SimpleInstructorDetail() {
		
	}


	public SimpleInstructorDetail(String youtubeChannel, String hobby) {
		this.youtubeChannel = youtubeChannel;
		this.hobby = hobby;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getYoutubeChannel() {
		return youtubeChannel;
	}


	public void setYoutubeChannel(String youtubeChannel) {
		this.youtubeChannel = youtubeChannel;
	}


	public String getHobby() {
		return hobby;
	}


	public void setHobby(String hobby) {
		this.hobby = hobby;
	}


	@Override
	public String toString() {
		return "SimpleInstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
	}


	public SimpleInstructor getSimpleInstructor() {
		return simpleInstructor;
	}


	public void setSimpleInstructor(SimpleInstructor simpleInstructor) {
		this.simpleInstructor = simpleInstructor;
	}
	
	
	
	
}

