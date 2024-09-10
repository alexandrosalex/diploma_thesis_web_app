package com.diplomawebapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diplomawebapp.model.Student;

public interface StudentDAO extends JpaRepository<Student, Integer> {
	
	public Student findByUsername(String username);
	
}