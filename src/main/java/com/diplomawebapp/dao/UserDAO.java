package com.diplomawebapp.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diplomawebapp.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {	
	
	Optional<User> findByUsername(String username);
	List<User> findAll();
}
