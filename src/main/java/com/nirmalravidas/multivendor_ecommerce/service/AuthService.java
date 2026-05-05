package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.request.LoginRequest;
import com.nirmalravidas.multivendor_ecommerce.request.SignupRequest;
import com.nirmalravidas.multivendor_ecommerce.response.AuthResponse;

public interface AuthService {
    String createUser(SignupRequest request) throws UserException;
    AuthResponse login(LoginRequest request) throws UserException, SellerException;
}
