package com.diplomawebapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diplomawebapp.dao.ApplicationDAO;
import com.diplomawebapp.model.Application;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Status;


@Service
public class ApplicationServiceImpl implements ApplicationService {


	
	@Autowired
    private  ApplicationDAO applicationDAO;

    public ApplicationServiceImpl(ApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }
    
    @Override
    public void saveApplication(Application application) {
        applicationDAO.save(application);
    }
    
    
    @Override
    public List<Application> findByStudentId(Integer studentId) {
      return applicationDAO.findByStudentId(studentId);
    }
    
    
    @Override
    public Application findById(Integer id) {
        Optional<Application> optionalApplication = applicationDAO.findById(id);
        return optionalApplication.orElse(null);
    }
    
    @Override
    public void deleteById(Integer id) {
        applicationDAO.deleteById(id);
    }
    
    @Override
    public List<Application> findBySubjectId(Integer subjectId) {
      return applicationDAO.findBySubjectId(subjectId);
    }
    
    
    @Override
    public List<Application> findBySubjectIdAndStatus(Integer subjectId, Status status) {
        return applicationDAO.findBySubjectIdAndStatus(subjectId, status);
    }
    
    
    @Override
    public List<Application> findByStudentIdAndStatus(Integer studentId, Status status) {
        return applicationDAO.findByStudentIdAndStatus(studentId, status);
    }
    

    
    @Override
    public Application findBySubjectAndStudent(Integer subjectId, Integer studentId) {
        return applicationDAO.findBySubjectIdAndStudentId(subjectId, studentId);
    }
    
    
    @Override
    public List<Application> findAcceptedApplicationsByProfessor(Professor professor) {
        return applicationDAO.findBySubjectProfessorAndStatus(professor, Status.ACCEPTED);
    }
    
    @Override
    public List<Application> findByStatus(Status status) {
        return applicationDAO.findByStatus(status);
    }

}