package com.diplomawebapp.service;

import org.springframework.stereotype.Service;


import com.diplomawebapp.model.Student;

@Service
public interface StudentService {
		
	public Student findByUsername(String username);
	public void save(Student student);	
	public Student getStudentById(Integer id);

}
