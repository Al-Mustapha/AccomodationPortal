package com.example.AccomodationPortal.Student;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomFetchUserDetails implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> student =
                Optional.ofNullable(studentRepository.findByUsername(username));
        return student.get();
//                student.orElseThrow(()->
//                new UsernameNotFoundException("User not found. Kindly check your details again."));
    }


}
