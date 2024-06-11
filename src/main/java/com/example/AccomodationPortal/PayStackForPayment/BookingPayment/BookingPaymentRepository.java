package com.example.AccomodationPortal.PayStackForPayment.BookingPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPaymentRepository extends JpaRepository<BookingPayment, Long> {
    BookingPayment findByReference(String reference);
}
