package com.example.fitnessonline.services;



public interface MailService {
    void sendEmail(String mail, String pin);

    void sendNotification(String mail, String notification);
}
