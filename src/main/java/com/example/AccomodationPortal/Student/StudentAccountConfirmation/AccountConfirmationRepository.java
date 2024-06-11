package com.example.AccomodationPortal.Student.StudentAccountConfirmation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountConfirmationRepository extends JpaRepository<AccountConfirmation, Long> {
    AccountConfirmation findByToken(String token);
}
