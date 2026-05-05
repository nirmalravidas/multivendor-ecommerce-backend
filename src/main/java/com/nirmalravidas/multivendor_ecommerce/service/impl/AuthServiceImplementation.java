package com.nirmalravidas.multivendor_ecommerce.service.impl;

import com.nirmalravidas.multivendor_ecommerce.config.JwtProvider;
import com.nirmalravidas.multivendor_ecommerce.enums.USER_ROLE;
import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.User;
import com.nirmalravidas.multivendor_ecommerce.repository.UserRepository;
import com.nirmalravidas.multivendor_ecommerce.request.LoginRequest;
import com.nirmalravidas.multivendor_ecommerce.request.SignupRequest;
import com.nirmalravidas.multivendor_ecommerce.response.AuthResponse;
import com.nirmalravidas.multivendor_ecommerce.service.AuthService;
import com.nirmalravidas.multivendor_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserServiceImplementation customUserServiceImplementation;

    @Override
    public String createUser(SignupRequest request) throws UserException {
        String email = request.getEmail();
        String fullName = request.getFullName();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email);

        if (user != null) {
            throw new UserException("User already exists with email: " + email);
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
        createdUser.setPassword(passwordEncoder.encode(password));

        log.info("User created: {}", createdUser.getEmail());

        userRepository.save(createdUser);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse login(LoginRequest request) throws UserException, SellerException {

        String username = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String password) throws SellerException {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if (userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
