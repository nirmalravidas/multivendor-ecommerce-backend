package com.nirmalravidas.multivendor_ecommerce.controller;

import com.nirmalravidas.multivendor_ecommerce.enums.USER_ROLE;
import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.request.LoginRequest;
import com.nirmalravidas.multivendor_ecommerce.request.SignupRequest;
import com.nirmalravidas.multivendor_ecommerce.response.AuthResponse;
import com.nirmalravidas.multivendor_ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody SignupRequest request) throws UserException {
        String token = authService.createUser(request);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws UserException, SellerException {
        AuthResponse authResponse = authService.login(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}