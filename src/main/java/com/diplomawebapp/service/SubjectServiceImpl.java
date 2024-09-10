package com.diplomawebapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diplomawebapp.dao.SubjectDAO;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Subject;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    @Override
    public Subject addSubject(Subject subject, Professor professor) {
        subject.setProfessor(professor);
        return subjectDAO.save(subject);
    }

    @Override
    public void deleteSubject(int subjectId) {
        subjectDAO.deleteById(subjectId);
    }
    
    public List<Subject> findByProfessor(Professor professor) {
        return subjectDAO.findByProfessor(professor);
    }
    public Subject findById(Integer id) {
        return subjectDAO.findById(id).orElse(null);
    }
    
    @Override
    public void updateSubject(Integer id, Subject updatedSubject) {
    	Subject existingSubject = subjectDAO.findById(id)
    			  .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        existingSubject.setTitle(updatedSubject.getTitle());
        existingSubject.setObjectives(updatedSubject.getObjectives());
        subjectDAO.save(existingSubject);
    }
      
    public List<Subject> findAll() {
        return subjectDAO.findAll();
    }

}