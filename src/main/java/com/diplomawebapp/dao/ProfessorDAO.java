package com.diplomawebapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diplomawebapp.model.Professor;

public interface ProfessorDAO extends JpaRepository<Professor, Integer> {	
	
	public Professor findByUsername(String username);
	
}
