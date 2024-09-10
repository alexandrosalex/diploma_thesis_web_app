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
import com.diplomawebapp.dao.SubjectDAO;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Subject;
import com.diplomawebapp.service.SubjectServiceImpl;

public class SubjectServiceImplTests {

    @Mock
    private SubjectDAO subjectDAO;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    @Test
    public void testAddSubject() {
        Professor professor = new Professor();
        Subject subject = new Subject();
        subject.setTitle("Mathematics");
        subject.setObjectives("Equations");
        when(subjectDAO.save(subject)).thenReturn(subject);
        Subject result = subjectService.addSubject(subject, professor);

        assertEquals(subject, result);

    }
    
    @Test
    public void testDeleteSubject() {
        int subjectId = 1;
        subjectService.deleteSubject(subjectId);
    }

    @Test
    public void testFindByProfessor() {
        Professor professor = new Professor();
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        subjects.add(new Subject());

        when(subjectDAO.findByProfessor(professor)).thenReturn(subjects);
        List<Subject> result = subjectService.findByProfessor(professor);

        assertEquals(subjects, result);
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Subject subject = new Subject();

        when(subjectDAO.findById(id)).thenReturn(Optional.of(subject));
        Subject result = subjectService.findById(id);

        assertEquals(subject, result);

    }



    @Test
    public void testUpdateSubject() {
        Integer id = 1;
        Subject existingSubject = new Subject();
        existingSubject.setId(id);
        existingSubject.setTitle("Mathematics");
        existingSubject.setObjectives("equations");

        Subject updatedSubject = new Subject();
        updatedSubject.setId(id);
        updatedSubject.setTitle("Physics");
        updatedSubject.setObjectives("Gravity");

        when(subjectDAO.findById(id)).thenReturn(Optional.of(existingSubject));
        subjectService.updateSubject(id, updatedSubject);

        verify(subjectDAO, times(1)).findById(id);
        verify(subjectDAO, times(1)).save(existingSubject);
        assertEquals("Physics", existingSubject.getTitle());
        assertEquals("Gravity", existingSubject.getObjectives());
    }


    @Test
    public void testFindAll() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        subjects.add(new Subject());

        when(subjectDAO.findAll()).thenReturn(subjects);
        List<Subject> result = subjectService.findAll();

        assertEquals(subjects, result);
        verify(subjectDAO, times(1)).findAll();
    }
}
