package com.example.AccomodationPortal.PayStackForPayment.BookingPayment;

import com.example.AccomodationPortal.PayStackForPayment.PaymentStatus;
import com.example.AccomodationPortal.Room.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BookingPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double amount;
    private String reference;
    private String email;
    private LocalDate paymentDate = LocalDate.now();
    private LocalDateTime  paymentTime = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "roomId",
            referencedColumnName = "id"
    )
    private Room room;

    public BookingPayment(Room room, BookingPayment appointmentPayment){
        this.room = room;
    }
    public BookingPayment() {
    }
}
