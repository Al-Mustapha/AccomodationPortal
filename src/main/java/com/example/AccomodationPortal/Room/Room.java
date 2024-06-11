package com.example.AccomodationPortal.Room;

import com.example.AccomodationPortal.PayStackForPayment.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String block;
    @Column(nullable = false)
    private String roomNumber;
    private boolean available=true;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}