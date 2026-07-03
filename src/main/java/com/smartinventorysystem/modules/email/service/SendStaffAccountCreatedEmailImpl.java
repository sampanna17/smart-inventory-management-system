package com.smartinventorysystem.modules.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendStaffAccountCreatedEmailImpl  implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendStaffAccountCreatedEmail(String toEmail, String fullName, String tempPassword) {

        String subject = "Your Staff Account Has Been Created";

        String body = "Hello " + fullName + ",\n\n" +
                "Your staff account has been created successfully.\n\n" +
                "Login Details:\n" +
                "Email: " + toEmail + "\n" +
                "Temporary Password: " + tempPassword + "\n\n" +
                "Please change your password after first login.\n\n" +
                "Thank you.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
