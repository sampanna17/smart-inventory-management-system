package com.smartinventorysystem.modules.email.service;

public interface EmailService {

    void sendStaffAccountCreatedEmail(String toEmail, String fullName, String tempPassword);
}
