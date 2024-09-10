package com.diplomawebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Student;
import com.diplomawebapp.model.User;
import com.diplomawebapp.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/users/{username}/profile", method = RequestMethod.GET)
	public String showUserProfile(@PathVariable("username") String username, Model model) {
	    User user = userService.findByUsername(username);
	    if (user instanceof Professor) {
	        model.addAttribute("professor", (Professor) user);
	        return "professorProfile";
	    } else if (user instanceof Student) {
	        model.addAttribute("student", (Student) user);
	        return "studentProfile";
	    } else {
	        return "error";
	    }
	}

}
