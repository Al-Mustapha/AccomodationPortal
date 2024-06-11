package com.example.AccomodationPortal.Student;

import com.example.AccomodationPortal.Authorization.UserRole;
import com.example.AccomodationPortal.GmailService.GmailService;
import com.example.AccomodationPortal.Security.PasswordEncoderClass;
import com.example.AccomodationPortal.Student.StudentAccountConfirmation.AccountConfirmation;
import com.example.AccomodationPortal.Student.StudentAccountConfirmation.AccountConfirmationRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {

    private final PasswordEncoderClass passwordEncoder;
    private final StudentRepository studentRepository;

    private final AccountConfirmationRepository accountConfirmationRepository;

    private final GmailService gmailService;
    public void saveStudent(Student student, ServletRequest servletRequest) throws MessagingException {
        student.setPassword(passwordEncoder.passwordEncoder().encode(student.getPassword()));
        student.setRole(UserRole.STUDENT);
        String token = UUID.randomUUID().toString();
        AccountConfirmation accountConfirmation =
                new AccountConfirmation(student, token);
        accountConfirmationRepository.save(accountConfirmation);

        String url =
              "http://localhost:8083/student/v1/confirmAccount?token=" + token;

        gmailService.sendMail(student.getEmail(),
                "Account Verification Link",
                "Click <a href='" + url + "'>here</a> to verify your account");
    }
    public String updateProfile(String registrationNumber, Student student) {
        Student updatedStudent =
                studentRepository.findById(registrationNumber).get();
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setMiddleName(student.getMiddleName());
        updatedStudent.setLastName(student.getLastName());
        updatedStudent.setEmail(student.getEmail());
        updatedStudent.setFaculty(student.getFaculty());
        updatedStudent.setDepartment(student.getDepartment());
        updatedStudent.setLevel(student.getLevel());
        studentRepository.save(updatedStudent);
        return "Profile updated successfully";
    }

    public Student getStudentProfile(String registrationNumber) {
        return studentRepository.findById(registrationNumber).get();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public String confirmAccount(String token) {
        AccountConfirmation accountConfirmation =
                accountConfirmationRepository.findByToken(token);

        Calendar calendar =
                Calendar.getInstance();

        if(token.equals("")){
            return "No token provided for verification";
        }
        if (accountConfirmation.getExpirationTime().getTime() - calendar.getTime().getTime() <=0 ){
            accountConfirmationRepository.delete(accountConfirmation);
            return "Token has expired";
        }

        Student student =
                accountConfirmation.getStudent();
        student.setEnabled(true);
        studentRepository.save(student);
        return "Account verified";
    }
}