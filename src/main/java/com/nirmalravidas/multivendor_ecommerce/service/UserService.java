package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws UserException;
    public User findUserByEmail(String email) throws UserException;
}
