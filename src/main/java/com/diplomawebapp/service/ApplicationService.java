package com.diplomawebapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.diplomawebapp.model.Application;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Status;


@Service
public interface ApplicationService {
    void saveApplication(Application application);
    public List<Application> findByStudentId(Integer studentId);
    Application findById(Integer id);
    public void deleteById(Integer id);
    public List<Application> findBySubjectId(Integer subjectId);
    List<Application> findBySubjectIdAndStatus(Integer subjectId, Status status);
    public List<Application> findByStudentIdAndStatus(Integer studentId, Status status);
    public Application findBySubjectAndStudent(Integer subjectId, Integer studentId);
    List<Application> findAcceptedApplicationsByProfessor(Professor professor);
    public List<Application> findByStatus(Status status);

}
