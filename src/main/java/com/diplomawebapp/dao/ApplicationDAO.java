package com.diplomawebapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diplomawebapp.model.Application;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Status;

public interface ApplicationDAO extends JpaRepository<Application, Integer> {
	
	List<Application> findByStudentId(Integer id);
	List<Application> findBySubjectId(Integer id);
	List<Application> findBySubjectIdAndStatus(Integer subjectId, Status status);
	List<Application> findByStudentIdAndStatus(Integer studentId, Status status);
	Application findBySubjectIdAndStudentId(Integer subjectId, Integer studentId);
	List<Application> findBySubjectProfessorAndStatus(Professor professor, Status status);
	List<Application> findByStatus(Status status);

}
