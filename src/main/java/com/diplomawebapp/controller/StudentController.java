package com.diplomawebapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Status;
import com.diplomawebapp.model.Student;
import com.diplomawebapp.model.User;
import com.diplomawebapp.service.ApplicationService;
import com.diplomawebapp.service.ProfessorService;
import com.diplomawebapp.service.StudentService;
import com.diplomawebapp.service.SubjectService;
import com.diplomawebapp.service.UserService;
import com.diplomawebapp.model.Subject;
import com.diplomawebapp.model.Application;
import java.util.ArrayList;

@Controller
public class StudentController {

    @RequestMapping("/student/dashboard")
    public String getStudentHome(){
        return "homepage";
    }

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping("/student/view_profile")
    public String getStudentProfile(Model model, Authentication authentication) {
        User user = studentService.findByUsername(authentication.getName());
        Student student = (Student) user;
        model.addAttribute("student", student);
        return "studentProfile";

    }
    
    @GetMapping("/student/edit_profile_student")
    public String editStudentProfile(Model model, Authentication authentication) {
        User user = studentService.findByUsername(authentication.getName());
        Student student = (Student) user;
        model.addAttribute("student", student);
        return "student/editProfile";
    }

    @PostMapping("/student/edit_profile_student")
    public String updateStudentProfile(@ModelAttribute("student") Student student, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
        User user = studentService.findByUsername(authentication.getName());
        Student currentStudent = (Student) user;
        currentStudent.setFullname(student.getFullname());
        currentStudent.setYearofstudies(student.getYearofstudies());
        currentStudent.setCurrentaveragegrade(student.getCurrentaveragegrade());
        currentStudent.setCoursesforgraduation(student.getCoursesforgraduation());
        studentService.save(currentStudent);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/student/view_profile";

    }
    
    @RequestMapping("student/show_users")
    public String getUsers(Model model, Authentication authentication) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "usersList";
    }
    
    
    @RequestMapping("/student/show_available_subjects")
    public String getStudentHome(Model model) {
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        return "student/availableSubjects";
    }
    

    @RequestMapping("/subject_details/{id}")
    public String showSubjectDetails(@PathVariable("id") Integer id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        return "student/subjectDetails";
    }
    
    @RequestMapping("/professor_profile/{id}")
    public String showProfessorProfile(@PathVariable("id") Integer id, Model model) {
        Professor professor = professorService.findById(id);
        model.addAttribute("professor", professor);
        return "professorProfile";
    }
    
    @RequestMapping(value = "/apply_to_subject/{id}", method = RequestMethod.POST)
    public String applyForSubject(@PathVariable("id") Integer id, Authentication authentication, RedirectAttributes redirectAttributes) {
        Student student = studentService.findByUsername(authentication.getName());
        Subject subject = subjectService.findById(id);
        List<Application> applications = applicationService.findByStudentId(student.getId());
        List<Application> subjectApplications = applicationService.findBySubjectId(subject.getId());

        for (Application app : applications) {
            if (app.getStatus() == Status.ACCEPTED) {
            	redirectAttributes.addFlashAttribute("errorMessage", "You have already been assigned in another subject. Check your applications.");
                return "redirect:/student/show_available_subjects";
            }
            if (app.getSubject().getId() == subject.getId()) {
            	redirectAttributes.addFlashAttribute("errorMessage", "You have already applied for this subject.");
                return "redirect:/student/show_my_applications";
            }
        }

        for (Application app : subjectApplications) {
            if (app.getStatus() == Status.ACCEPTED) {
            	redirectAttributes.addFlashAttribute("errorMessage", "Subject is already assigned.");
                return "redirect:/student/show_available_subjects";
            }
        }

        Application application = new Application(student, subject, Status.PENDING);
        applicationService.saveApplication(application);
        redirectAttributes.addFlashAttribute("successMessage", "You successfully applied for this subject!");
        return "redirect:/student/show_my_applications";
    }




   
    @GetMapping("/student/show_my_applications")
    public String getMyApplications(Model model, Authentication authentication) {
        String username = authentication.getName();
        Student student = studentService.findByUsername(username);
        List<Application> applications = applicationService.findByStudentId(student.getId());
        List<Subject> subjects = new ArrayList<>();
        for (Application application : applications) {
            Subject subject = subjectService.findById(application.getSubject().getId());
            subjects.add(subject);
        }
        model.addAttribute("applications", applications);
        model.addAttribute("subjects", subjects);
        return "student/myApplications";
    }

    @RequestMapping("/remove_application/{studentId}/{subjectId}")
    public String removeApplication(@PathVariable("studentId") Integer studentId, @PathVariable("subjectId") Integer subjectId, RedirectAttributes redirectAttributes) {
        Application application = applicationService.findBySubjectAndStudent(subjectId, studentId);
        if (application != null) {
            if (application.getStatus().equals(Status.ACCEPTED)) {
            	redirectAttributes.addFlashAttribute("errorMessage", "Can not remove accepted application");
                return "redirect:/student/show_my_applications";
            } else {
                applicationService.deleteById(application.getId());
                redirectAttributes.addFlashAttribute("successMessage", "Application removed successfully!");
               
            }
        }
        return "redirect:/student/show_my_applications";
    }
    
    @GetMapping("/grades/{applicationId}")
    public String viewGrades(@PathVariable("applicationId") Integer applicationId, Model model) {
        Application application = applicationService.findById(applicationId);
        model.addAttribute("app", application);
        return "student/grades";
    } 
  

}
