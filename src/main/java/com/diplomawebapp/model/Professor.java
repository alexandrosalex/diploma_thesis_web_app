package com.diplomawebapp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Professor extends User  {
	
	public Professor() {
		
	}
	
	public Professor(String username, String password) {
	    super();
	    this.setUsername(username);
	    this.setPassword(password);
	    this.setRole(Role.PROFESSOR);
	}
	
    @Column(name="full_name")
    private String fullname;
    
    
    public String getFullname() {
		return fullname;
	}
        
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
    @Column(name="specialty")
    private String specialty;
    
    
	public String getSpecialty() {
		return specialty;
	}   
    
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

}
