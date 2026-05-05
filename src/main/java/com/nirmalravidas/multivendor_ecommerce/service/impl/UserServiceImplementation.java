package com.nirmalravidas.multivendor_ecommerce.service.impl;

import com.nirmalravidas.multivendor_ecommerce.config.JwtProvider;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.User;
import com.nirmalravidas.multivendor_ecommerce.repository.UserRepository;
import com.nirmalravidas.multivendor_ecommerce.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImplementation(
            UserRepository userRepository,
            JwtProvider jwtProvider
    ) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UserException("User not exist with email "+email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String username) throws UserException {
        User user = userRepository.findByEmail(username);

        if (user != null){
            return user;
        }

        throw new UserException("user not exist with username "+username);
    }
}
