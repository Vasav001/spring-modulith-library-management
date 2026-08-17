package com.vasav.springmodulithlibrarymanagement.identity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendVerificationEmail(String toEmail, String verificationToken) {
        log.info("Verification link for {}: /api/v1/auth/verify-email?token={}", toEmail, verificationToken);
    }
}