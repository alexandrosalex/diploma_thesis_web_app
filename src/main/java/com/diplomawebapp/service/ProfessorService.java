package com.diplomawebapp.service;

import org.springframework.stereotype.Service;

import com.diplomawebapp.model.Professor;

@Service
public interface ProfessorService {
	
	public Professor findByUsername(String username);
	public void save(Professor professor);
	public Professor findById(Integer id);
}
