package com.farmconnect.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.farmconnect.backend.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            if (fromAddress == null || fromAddress.isBlank()) {
                log.warn("SMTP username is not configured. Skipping email send to {}. Email body: {}", to, body);
                return;
            }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (MailException ex) {
            // Do not break registration during local development if SMTP is not configured.
            // The OTP is printed in backend logs so you can still test the frontend flow.
            log.warn("Could not send email to {}. Configure FARMCONNECT_MAIL_USERNAME and FARMCONNECT_MAIL_PASSWORD. Email body: {}", to, body, ex);
        }
    }
}
