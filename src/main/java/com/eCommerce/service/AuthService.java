package com.eCommerce.service;

import com.eCommerce.payload.AuthenticationResult;
import com.eCommerce.payload.UserResponse;
import com.eCommerce.security.jwt.LoginRequest;
import com.eCommerce.security.jwt.MessageResponse;
import com.eCommerce.security.jwt.SignUpRequest;
import com.eCommerce.security.jwt.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface AuthService {

    AuthenticationResult login(LoginRequest loginRequest);

    ResponseEntity<MessageResponse> register(SignUpRequest signUpRequest);

    UserInfoResponse getCurrentUserDetails(Authentication authentication);

    ResponseCookie logoutUser();

    UserResponse getAllSellers(Pageable pageable);
}