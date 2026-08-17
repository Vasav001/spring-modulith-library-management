package com.vasav.springmodulithlibrarymanagement.identity.service;

public interface EmailService {

    void sendVerificationEmail(String toEmail, String verificationToken);
}