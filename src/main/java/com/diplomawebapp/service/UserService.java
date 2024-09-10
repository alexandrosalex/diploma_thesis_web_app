package com.diplomawebapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diplomawebapp.model.User;

@Service
public interface UserService {	
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    public List<User> findAllUsers();
    public User findByUsername(String username);
}
