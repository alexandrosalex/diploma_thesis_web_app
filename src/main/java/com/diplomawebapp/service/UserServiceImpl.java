package com.diplomawebapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.diplomawebapp.dao.ProfessorDAO;
import com.diplomawebapp.dao.StudentDAO;
import com.diplomawebapp.dao.UserDAO;
import com.diplomawebapp.model.Role;
import com.diplomawebapp.model.User;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Student;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDAO userDAO;
		
	@Autowired
	private ProfessorDAO professorDAO;
	
	@Autowired
	private StudentDAO studentDAO;

	@Override
	public void saveUser(User user) {
	    String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
	    if (user.getRole() == Role.PROFESSOR) {
	        Professor professor = new Professor(user.getUsername(), encodedPassword);
	        professorDAO.save(professor);
	    } else if (user.getRole() == Role.STUDENT) {
	        Student student = new Student(user.getUsername(), encodedPassword);
	        studentDAO.save(student);
	    }
	}

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userDAO.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND", username)
	                ));
	}
	
    @Override
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }
    
    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = userDAO.findByUsername(username);
        return userOptional.orElse(null);
    }
		
}
