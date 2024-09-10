package com.diplomawebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diplomawebapp.dao.ProfessorDAO;
import com.diplomawebapp.model.Professor;

@Service
public class ProfessorServiceImpl implements ProfessorService {
		
	@Autowired
	private ProfessorDAO professorDAO;
		
	@Override
	public Professor findByUsername(String username) {
	    return professorDAO.findByUsername(username);
	}
	@Override
	public void save(Professor professor) {
		professorDAO.save(professor);
	}
	
	@Override
	public Professor findById(Integer id) {
	    return professorDAO.findById(id).orElse(null);
	}

}
