package com.example.fitnessonline.services.impl;

import com.example.fitnessonline.services.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String mail, String pin) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail);
        message.setSubject("Account activation");
        message.setText("Your PIN is " + pin + ". Enter this PIN to activate your account.");
        javaMailSender.send(message);
    }

    @Override
    public void sendNotification(String mail, String notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail);
        message.setSubject("Category notification");
        message.setText(notification);
        javaMailSender.send(message);
    }
}
