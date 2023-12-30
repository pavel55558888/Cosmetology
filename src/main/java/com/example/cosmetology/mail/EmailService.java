package com.example.cosmetology.mail;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
