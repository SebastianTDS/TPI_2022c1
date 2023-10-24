package com.fantasticos.calendarservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(String to, String email, String nameEvent) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("Reminder for the " + nameEvent + " event");
            helper.setFrom("iumach.app@gmail.com");
            mailSender.send(mimeMessage);
            log.info("Email send");
        }catch (MessagingException e){
            log.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
