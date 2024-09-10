package com.diplomawebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diplomawebapp.dao.StudentDAO;
import com.diplomawebapp.model.Student;

@Service
public class StudentServiceImpl implements StudentService{
	
	
	@Autowired
	private StudentDAO studentDAO;
	
	
	@Override
	public Student findByUsername(String username) {
	    return studentDAO.findByUsername(username);
	}
	@Override
	public void save(Student student) {
		studentDAO.save(student);
	}
	
	@Override
	public Student getStudentById(Integer id) {
	    return studentDAO.findById(id).orElseThrow(() -> new RuntimeException("Could not find student with id: " + id));
	}

}
