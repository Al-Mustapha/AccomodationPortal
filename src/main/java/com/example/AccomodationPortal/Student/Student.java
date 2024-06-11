package com.example.AccomodationPortal.Student;

import com.example.AccomodationPortal.Authorization.UserRole;
import com.example.AccomodationPortal.Room.Room;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Data
public class Student implements UserDetails {
    @Id
    private String registrationNumber;
    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String faculty;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private String level;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean enabled = isEnabled();

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "roomAllocated",
            unique = true
    )
    private Room room;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissionsSet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
