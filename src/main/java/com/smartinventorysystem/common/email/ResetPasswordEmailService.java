package com.smartinventorysystem.common.email;

public interface  ResetPasswordEmailService {
    void sendResetPasswordEmail(String toEmail, String fullName, String token);
}
