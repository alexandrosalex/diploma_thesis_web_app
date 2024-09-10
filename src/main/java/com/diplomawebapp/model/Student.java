package com.diplomawebapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {
	
	public Student() {
		   
	}
	
	public Student(String username, String password) {
	    super();
	    this.setUsername(username);
	    this.setPassword(password);
	    this.setRole(Role.STUDENT);
	}

    @Column(name="full_name")
    private String fullname;
       
    public String getFullname() {
		return fullname;
	}   
    
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	@Column(name="year_of_studies")
	private int yearofstudies; 
	
	public int getYearofstudies() {
		return yearofstudies;
	}

	public void setYearofstudies(int yearofstudies) {
		this.yearofstudies = yearofstudies;
	}
	
	@Column(name="current_average_grade")
	private double currentaveragegrade; 
	
	public double getCurrentaveragegrade() {
		return currentaveragegrade;
	}

	public void setCurrentaveragegrade(double currentaveragegrade) {
		this.currentaveragegrade = currentaveragegrade;
	}
	
	@Column(name="courses_for_graduation")
	private int coursesforgraduation; 
	
	public int getCoursesforgraduation() {
		return coursesforgraduation;
	}

	public void setCoursesforgraduation(int coursesforgraduation) {
		this.coursesforgraduation = coursesforgraduation;
	}

}
