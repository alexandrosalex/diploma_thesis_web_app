package com.diplomawebapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.diplomawebapp.dao.ApplicationDAO;
import com.diplomawebapp.model.Application;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Status;
import com.diplomawebapp.service.ApplicationServiceImpl;

public class ApplicationServiceImplTests {

    @Mock
    private ApplicationDAO applicationDAO;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindByStudentId() {
        Integer studentId = 1;
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        applications.add(new Application());

        when(applicationDAO.findByStudentId(studentId)).thenReturn(applications);
        List<Application> result = applicationService.findByStudentId(studentId);

        assertEquals(applications, result);

    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Application application = new Application();

        when(applicationDAO.findById(id)).thenReturn(Optional.of(application));
        Application result = applicationService.findById(id);

        assertEquals(application, result);

    }

    @Test
    public void testFindByIdNotFound() {
        Integer id = 1;

        when(applicationDAO.findById(id)).thenReturn(Optional.empty());
        Application result = applicationService.findById(id);
        assertEquals(null, result);
   
    }

    @Test
    public void testDeleteById() {
        Integer id = 1;

        applicationService.deleteById(id);

    }

    @Test
    public void testFindBySubjectId() {
        Integer subjectId = 1;
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        applications.add(new Application());

        when(applicationDAO.findBySubjectId(subjectId)).thenReturn(applications);
        List<Application> result = applicationService.findBySubjectId(subjectId);

        assertEquals(applications, result);

    }

    @Test
    public void testFindBySubjectIdAndStatus() {
        Integer subjectId = 1;
        Status status = Status.ACCEPTED;
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        applications.add(new Application());

        when(applicationDAO.findBySubjectIdAndStatus(subjectId, status)).thenReturn(applications);
        List<Application> result = applicationService.findBySubjectIdAndStatus(subjectId, status);

        assertEquals(applications, result);

    }

    @Test
    public void testFindByStudentIdAndStatus() {
        Integer studentId = 1;
        Status status = Status.ACCEPTED;
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        applications.add(new Application());

        when(applicationDAO.findByStudentIdAndStatus(studentId, status)).thenReturn(applications);
        List<Application> result = applicationService.findByStudentIdAndStatus(studentId, status);

        assertEquals(applications, result);

    }

    @Test
    public void testFindBySubjectAndStudent() {
        Integer subjectId = 1;
        Integer studentId = 1;
        Application application = new Application();

        when(applicationDAO.findBySubjectIdAndStudentId(subjectId, studentId)).thenReturn(application);
        Application result = applicationService.findBySubjectAndStudent(subjectId, studentId);

        assertEquals(application, result);

    }

    @Test
    public void testFindAcceptedApplicationsByProfessor() {
        Professor professor = new Professor();
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        applications.add(new Application());

        when(applicationDAO.findBySubjectProfessorAndStatus(professor, Status.ACCEPTED)).thenReturn(applications);
        List<Application> result = applicationService.findAcceptedApplicationsByProfessor(professor);

        assertEquals(applications, result);
       
    }

    @Test
    public void testFindByStatus() {
        Status status = Status.ACCEPTED;
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        applications.add(new Application());

        when(applicationDAO.findByStatus(status)).thenReturn(applications);
        List<Application> result = applicationService.findByStatus(status);

        assertEquals(applications, result);
    }
}

