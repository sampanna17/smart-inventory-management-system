package com.smartinventorysystem.modules.email.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

}
