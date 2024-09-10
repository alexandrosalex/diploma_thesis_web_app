package com.diplomawebapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.diplomawebapp.dao.ProfessorDAO;
import com.diplomawebapp.dao.StudentDAO;
import com.diplomawebapp.dao.UserDAO;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Role;
import com.diplomawebapp.model.Student;
import com.diplomawebapp.model.User;
import com.diplomawebapp.service.UserServiceImpl;

public class UserServiceImplTests {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ProfessorDAO professorDAO;

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    public void testSaveUserProfessor() {

        User user = new User();
        user.setUsername("TexnlogProfessor");
        user.setPassword("uoi2023");
        user.setRole(Role.PROFESSOR);
        Professor professor = new Professor(user.getUsername(), user.getPassword());
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(professorDAO.save(professor)).thenReturn(professor);
        userService.saveUser(user);

        assertEquals(professor, professorDAO.save(professor));
    }
    

    @Test
    public void testSaveUserStudent() {

        User user = new User();
        user.setUsername("TexnlogStudent");
        user.setPassword("uoi2023");
        user.setRole(Role.STUDENT);
        Student student = new Student(user.getUsername(), user.getPassword());
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(studentDAO.save(student)).thenReturn(student);
        userService.saveUser(user);

        assertEquals(student, studentDAO.save(student));
    }
    
    
    @Test
    public void testIsUserPresent() {

        User user = new User();
        user.setUsername("existingUser");
        when(userDAO.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        boolean isPresent = userService.isUserPresent(user);

        assertEquals(Optional.of(user), userDAO.findByUsername(user.getUsername()));
        assertEquals(true, isPresent);
    }
    
    @Test
    public void testIsUserNotPresent() {

        User user = new User();
        user.setUsername("nonExistingUser");
        when(userDAO.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        boolean isPresent = userService.isUserPresent(user);
        assertEquals(Optional.empty(), userDAO.findByUsername(user.getUsername()));
        assertEquals(false, isPresent);
    }

    
    @Test
    public void testFindAllUsers() {

        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        userList.add(user1);
        userList.add(user2);
        
        when(userDAO.findAll()).thenReturn(userList);
        List<User> result = userService.findAllUsers();

        assertEquals(userList, userDAO.findAll());
        assertEquals(userList, result);
    }

 
}
