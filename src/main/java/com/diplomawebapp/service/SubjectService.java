package com.diplomawebapp.service;

import java.util.List;

import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Subject;

public interface SubjectService {
    Subject addSubject(Subject subject, Professor professor);
    void deleteSubject(int subjectId);
    public List<Subject> findByProfessor(Professor professor);
    public Subject findById(Integer id);
    public void updateSubject(Integer id, Subject updatedSubject);
    public List<Subject> findAll();    
}
