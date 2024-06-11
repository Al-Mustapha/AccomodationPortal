package com.example.AccomodationPortal.PayStackForPayment.BookingPayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room/")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingPaymentController {
    @Autowired
    private BookingPaymentService bookingPaymentService;

    @PostMapping("pay/{roomId}")
    public ResponseEntity<String> pay(@PathVariable("roomId") Long roomId,
                                      HttpServletRequest request
                      ) throws JsonProcessingException, JSONException {
        return bookingPaymentService.pay(roomId, request);
    }

    @GetMapping("verify/{reference}")
    public ResponseEntity<String> verifyPayment(@PathVariable("reference") String reference)
            throws JSONException {
        return bookingPaymentService.verifyPayment(reference);
    }

    @PostMapping("paystack-webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) throws JSONException {
        return bookingPaymentService.handleWebhook(payload);
    }
}
