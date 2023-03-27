package com.lth.cookingassistant.mail;

public interface EmailService {
    String sendSimpleMail(String to, String subject, String link);
}
