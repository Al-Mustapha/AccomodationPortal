package com.example.AccomodationPortal.Student;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequestMapping("/student/v1/")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("createAccount")
    public String createAccount(@RequestBody Student student, HttpServletRequest httpServletRequest) throws MessagingException {
        studentService.saveStudent(student, httpServletRequest);
        return "Account created successfully";
    }

    @GetMapping("confirmAccount")
    public String confirmAccount(@RequestParam("token") String token){
        return studentService.confirmAccount(token);
    }

    @PutMapping("editProfile/{registrationNumber}")
    public void updateProfile(@PathVariable("registrationNumber") String registrationNumber,
                                @RequestBody Student student
                                ){
        studentService.updateProfile(registrationNumber, student);
    }

    @GetMapping("getProfile/{registrationNumber}")
    @PreAuthorize("hasRole('STUDENT')")
    public Student getProfile(@PathVariable("registrationNumber") String registrationNumber){
        return studentService.getStudentProfile(registrationNumber);
    }

    @GetMapping("getAllStudents")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }


}
