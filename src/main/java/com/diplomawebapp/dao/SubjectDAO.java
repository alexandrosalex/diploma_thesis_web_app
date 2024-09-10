package com.diplomawebapp.dao;

import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SubjectDAO extends JpaRepository<Subject, Integer> {
	
	List<Subject> findByProfessor(Professor professor);
}
