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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import com.diplomawebapp.model.Application;
import com.diplomawebapp.model.Professor;
import com.diplomawebapp.model.Status;
import com.diplomawebapp.model.Student;
import com.diplomawebapp.model.Subject;
import com.diplomawebapp.model.User;
import com.diplomawebapp.service.ApplicationService;
import com.diplomawebapp.service.ProfessorService;
import com.diplomawebapp.service.StudentService;
import com.diplomawebapp.service.SubjectService;
import com.diplomawebapp.service.UserService;

@Controller
public class ProfessorController {

    @RequestMapping("/professor/dashboard")
    public String getProfessorHome(){
        return "homepage";
    }
    
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private ApplicationService applicationService;
   
    @Autowired
    private StudentService studentService;
        
    @GetMapping("/professor/view_profile")
    public String getProfessorProfile(Model model, Authentication authentication) {
        User user = professorService.findByUsername(authentication.getName());
        Professor professor = (Professor) user;
        model.addAttribute("professor", professor);
        return "professorProfile";

    }
    
    @GetMapping("/professor/edit_profile_professor")
    public String editProfessorProfile(Model model, Authentication authentication) {
        User user = professorService.findByUsername(authentication.getName());
        Professor professor = (Professor) user;
        model.addAttribute("professor", professor);
        return "professor/editProfile";
    }

    @PostMapping("/professor/edit_profile_professor")
    public String updateProfessorProfile(@ModelAttribute("professor") Professor professor, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = professorService.findByUsername(authentication.getName());
        Professor currentProfessor = (Professor) user;
        currentProfessor.setFullname(professor.getFullname());
        currentProfessor.setSpecialty(professor.getSpecialty());
        professorService.save(currentProfessor);
        
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated!");
        
        return "redirect:/professor/view_profile";
    }


    
    @RequestMapping("/professor/show_users")
    public String getUsers(Model model, Authentication authentication) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "usersList";
    }
    
    
    @GetMapping("/professor/add_subject")
    public String showAddSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "professor/addSubjectForm";
    }
    
    @PostMapping("/professor/add_subject")
    public String addSubject(@ModelAttribute("subject") Subject subject, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
        User user = professorService.findByUsername(authentication.getName());
        Professor professor = (Professor) user;
        subjectService.addSubject(subject, professor);
        redirectAttributes.addFlashAttribute("successMessage", "Subject added successfully!");
        return "redirect:/professor/my_subject_list";
    }
    
    
    @RequestMapping("/professor/my_subject_list")
    public String viewMySubjectList(Model model, Authentication authentication) {
        User user = professorService.findByUsername(authentication.getName());
        Professor professor = (Professor) user;
        List<Subject> subjectList = subjectService.findByProfessor(professor);
        model.addAttribute("subjectList", subjectList);
        return "professor/mySubjectsList";
    }

    @RequestMapping("/professor/delete_subject")
    public String deleteSubject(@ModelAttribute("id") Integer id, RedirectAttributes redirectAttributes) {
        List<Application> applications = applicationService.findBySubjectIdAndStatus(id, Status.PENDING);
        if (!applications.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete subject. There are pending applications for this subject.");
            return "redirect:/professor/my_subject_list";
        }
        
        List<Application> subjectApplications = applicationService.findBySubjectId(id);
        for (Application application : subjectApplications) {
            applicationService.deleteById(application.getId());
        }
        
        subjectService.deleteSubject(id);
        
        redirectAttributes.addFlashAttribute("successMessage", "Subject deleted successfully!");
        return "redirect:/professor/my_subject_list";
    }



    
    @GetMapping("/professor/edit_subject")
    public String editSubject(@ModelAttribute("id") Integer id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        return "professor/editSubjectform";
    }
    

    @PostMapping("/professor/edit_subject")
    public String updateSubject(@ModelAttribute("subject") Subject subject,RedirectAttributes redirectAttributes) {
        subjectService.updateSubject(subject.getId(), subject);
        redirectAttributes.addFlashAttribute("successMessage", "Subject updated successfully!");
        return "redirect:/professor/my_subject_list";
    }
    
    @GetMapping("/professor/show_applicants")
    public String viewApplicants(@RequestParam("subjectId") Integer subjectId, Model model) {
        List<Application> applications = applicationService.findBySubjectId(subjectId);
        model.addAttribute("applications", applications);
        return "professor/viewApplicants";
    }
    
    @RequestMapping("/student_profile/{id}")
    public String showStudentProfile(@PathVariable("id") Integer studentId, Model model) {
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        return "studentProfile";
    }

    
 
    
    @RequestMapping("/professor/assign_thesis_manually")
    public String assignSubject(
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam("studentId") Integer studentId,
            Model model) {
    	

        List<Application> acceptedApplications = applicationService.findBySubjectIdAndStatus(subjectId, Status.ACCEPTED);
        if (!acceptedApplications.isEmpty()) {
            model.addAttribute("errorMessage", "Subject is already assigned");
        } else if (!applicationService.findByStudentIdAndStatus(studentId, Status.ACCEPTED).isEmpty()) {

            model.addAttribute("errorMessage", "Student is not available (already assigned)");
        } else {
            List<Application> pendingApplications = applicationService.findBySubjectIdAndStatus(subjectId, Status.PENDING);
            if (pendingApplications.isEmpty()) {
                model.addAttribute("errorMessage", "There are no pending applications for this subject");
            } else {

                List<Application> rejectedApplications = applicationService.findBySubjectId(subjectId);
                for (Application application : rejectedApplications) {
                    application.setStatus(Status.REJECTED);
                    applicationService.saveApplication(application);
                }
                List<Application> rejectedApplicationsStudent = applicationService.findByStudentId(studentId);
                for (Application application : rejectedApplicationsStudent) {
                    application.setStatus(Status.REJECTED);
                    applicationService.saveApplication(application);
                }

                Application selectedApplication = applicationService.findBySubjectAndStudent(subjectId, studentId);
                selectedApplication.setStatus(Status.ACCEPTED);
                applicationService.saveApplication(selectedApplication);

                String fullname = selectedApplication.getStudent().getFullname();
                String title = selectedApplication.getSubject().getTitle();
                model.addAttribute("successMessage", "Subject '" + title + "' assigned successfully to " + fullname);
            }
        }

        model.addAttribute("applications", applicationService.findBySubjectId(subjectId));
        return "professor/viewApplicants";
    }


    @GetMapping("professor/my_assigned_subjects")
    public String viewMyAcceptedSubjects(Authentication authentication, Model model) {
        Professor professor = (Professor) authentication.getPrincipal();
        List<Subject> acceptedSubjects = new ArrayList<>();

        List<Application> applications = applicationService.findAcceptedApplicationsByProfessor(professor);
        for (Application application : applications) {
            acceptedSubjects.add(application.getSubject());
        }

        model.addAttribute("applications", applications);
        return "professor/myAssignedSubjects";
    }
    
    
    @GetMapping("/professor/set_grade")
    public String showGradeForm(@RequestParam("applicationId") Integer applicationId, Model model) {
        Application application = applicationService.findById(applicationId); 
        model.addAttribute("app", application);
        return "professor/grades";

    }


    
    
    @PostMapping("/professor/set_grade")
    public String setGrade(@RequestParam("applicationId") Integer applicationId,
                           @RequestParam("implementationgrade") Double implementationgrade,
                           @RequestParam("reportgrade") Double reportgrade,
                           @RequestParam("presentationgrade") Double presentationgrade,
                           RedirectAttributes redirectAttributes) {
        Application application = applicationService.findById(applicationId);
        if (application != null && application.getStatus() == Status.ACCEPTED) {
            application.setImplementationgrade(implementationgrade);
            application.setReportgrade(reportgrade);
            application.setPresentationgrade(presentationgrade);
            double finalgrade = 0.7 * implementationgrade + 0.15 * reportgrade + 0.15 * presentationgrade;
            application.setFinalgrade(finalgrade);
            applicationService.saveApplication(application);
            
            redirectAttributes.addFlashAttribute("successMessage", "The final grade for " + application.getStudent().getFullname() + " for the subject " + application.getSubject().getTitle() + " is " + finalgrade);
        }        
        return "redirect:/professor/my_assigned_subjects";
    }
    
    

}

      


