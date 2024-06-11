package com.example.AccomodationPortal.Student.StudentAccountConfirmation;

import com.example.AccomodationPortal.Student.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class AccountConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private Date expirationTime;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student"
    )
    private Student student;

    public AccountConfirmation(){

    }
    public AccountConfirmation(Student student, String token){
        this.student = student;
        this.token = token;
        expirationTime = calculateExpirationTime();
    }

    private Date calculateExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(calendar.MINUTE, 10);
        return new Date(calendar.getTime().getTime());
    }
}
