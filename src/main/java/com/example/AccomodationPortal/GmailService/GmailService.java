package com.example.AccomodationPortal.GmailService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class GmailService {


    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String toEMail, String toSubject, String body) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        SimpleMailMessage message = new SimpleMailMessage();
        mimeMessageHelper.setFrom("m@gmail.com");
        mimeMessageHelper.setSubject(toSubject);
        mimeMessageHelper.setTo(toEMail);
        mimeMessageHelper.setText(body, true);
        javaMailSender.send(mimeMessage);
    }


}
