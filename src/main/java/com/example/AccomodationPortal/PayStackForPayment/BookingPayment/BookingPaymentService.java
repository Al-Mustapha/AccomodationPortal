package com.example.AccomodationPortal.PayStackForPayment.BookingPayment;


import com.example.AccomodationPortal.PayStackForPayment.PaymentStatus;
import com.example.AccomodationPortal.Room.Room;
import com.example.AccomodationPortal.Room.RoomRepository;
import com.example.AccomodationPortal.Security.JwtUtils;
import com.example.AccomodationPortal.Student.Student;
import com.example.AccomodationPortal.Student.StudentRepository;
import org.json.JSONException;
import org.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class BookingPaymentService {
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private BookingPaymentRepository bookingPaymentRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Value("${paystack.secretKey}")
    private String key;
    public ResponseEntity<String> pay(Long roomId, HttpServletRequest request) throws JSONException {
        String url = "https://api.paystack.co/transaction/initialize";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(key);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Room room =
                roomRepository.findById(roomId).get();

        room.setPaymentStatus(PaymentStatus.NOT_PAID);
        String header  = request.getHeader("authorization");
        String token = header.substring(7);
        String username = jwtUtils.extractUsername(token);
        Student student =
                studentRepository.findByUsername(username);
        BookingPayment bookingPayment =
                new BookingPayment();
        bookingPayment.setAmount(7000);
        bookingPayment.setEmail(student.getEmail());
        bookingPayment.setName("Booking Payment");


        HttpEntity<BookingPayment> entity = new HttpEntity<>(bookingPayment, httpHeaders);
         ResponseEntity<String> responseEntity = restTemplate.exchange(
           url,
           HttpMethod.POST,
           entity,
           String.class
         );
         JSONObject jsonObject = new JSONObject(responseEntity.getBody());
//        String checkout = jsonObject.getJSONObject("data").getString("authorization_url");
        bookingPayment.setReference(jsonObject.getJSONObject("data").getString("reference"));
        bookingPayment.setStatus(PaymentStatus.NOT_PAID);
        bookingPayment.setPaymentDate(LocalDate.now());
        bookingPayment.setPaymentTime(LocalDateTime.now());
        bookingPayment.setRoom(room);

//        BookingPayment forSave = new BookingPayment(
//                room, bookingPayment
//        );
        bookingPaymentRepository.save(bookingPayment);

//        roomRepository.save(room);


         if (responseEntity.getStatusCode() == HttpStatus.OK){
             return ResponseEntity.ok(responseEntity.getBody());
         }
         else {
             return new ResponseEntity<String>("Payment initialization failed", HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    public ResponseEntity<String> verifyPayment(String reference) throws JSONException {
        String url = "https://api.paystack.co/transaction/verify";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(key);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        BookingPayment bookingPayment =
                bookingPaymentRepository.findByReference(reference);

        String verifyPayment = "https://api.paystack.co/transaction/verify/" + reference;
        ResponseEntity<String> verifyResponse =
                restTemplate.exchange(
                        verifyPayment,
                        HttpMethod.GET,
                        entity,
                        String.class
                );
        JSONObject jsonObject = new JSONObject(verifyResponse.getBody());
        String status = jsonObject.getJSONObject("data").getString("status");
        if (verifyResponse.getStatusCode() == HttpStatus.OK){
            Room room =
                    bookingPayment.getRoom();
            room.setPaymentStatus(PaymentStatus.PAID);
            bookingPayment.setStatus(PaymentStatus.PAID);

            BookingPayment bookingPayment1 =
                    new BookingPayment(room, bookingPayment);
            bookingPaymentRepository.save(bookingPayment1);

            return ResponseEntity.ok(verifyResponse.getBody());
        }
        else {
            return ResponseEntity.internalServerError().body("Payment verification failed");
        }

    }

    public ResponseEntity<String> handleWebhook(String payload) throws JSONException {
        JSONObject jsonObject = new JSONObject(payload);

        String status = jsonObject.getJSONObject("data").getString("status");
        if (status.equalsIgnoreCase("success")){
            BookingPayment bookingPayment =
                    bookingPaymentRepository.findByReference(
                            jsonObject.getJSONObject("data").getString("reference"));
            Room room =
                    bookingPayment.getRoom();
            bookingPayment.setStatus(PaymentStatus.PAID);
            room.setPaymentStatus(PaymentStatus.PAID);
            roomRepository.save(room);
//            AppointmentPayment appointmentPayment1 =
//                    new AppointmentPayment(appointment, appointmentPayment);
            bookingPaymentRepository.save(bookingPayment);
        }


        System.out.println(payload);
        return ResponseEntity.ok(payload);
    }

}
