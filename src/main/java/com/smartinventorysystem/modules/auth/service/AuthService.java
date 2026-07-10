package com.smartinventorysystem.modules.auth.service;

import com.smartinventorysystem.modules.auth.dto.request.ActivateAccountRequest;
import com.smartinventorysystem.modules.auth.dto.request.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.request.ResendActivationRequest;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.request.SignupRequest;

public interface AuthService {

    AuthResponse  signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String token);

    void activateAccount(ActivateAccountRequest request);

    void resendActivationLink(ResendActivationRequest request);

}
