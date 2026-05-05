package com.nirmalravidas.multivendor_ecommerce.controller;

import com.nirmalravidas.multivendor_ecommerce.config.JwtProvider;
import com.nirmalravidas.multivendor_ecommerce.enums.AccountStatus;
import com.nirmalravidas.multivendor_ecommerce.enums.USER_ROLE;
import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.model.Seller;
import com.nirmalravidas.multivendor_ecommerce.model.SellerReport;
import com.nirmalravidas.multivendor_ecommerce.repository.SellerRepository;
import com.nirmalravidas.multivendor_ecommerce.request.LoginRequest;
import com.nirmalravidas.multivendor_ecommerce.response.AuthResponse;
import com.nirmalravidas.multivendor_ecommerce.service.SellerReportService;
import com.nirmalravidas.multivendor_ecommerce.service.SellerService;
import com.nirmalravidas.multivendor_ecommerce.service.impl.CustomUserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final JwtProvider jwtProvider;
    private final CustomUserServiceImplementation customUserServiceImplementation;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws SellerException {
        Seller seller = sellerService.getSellerByEmail(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), seller.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        UsernamePasswordAuthenticationToken authentication = authenticate(req.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        authResponse.setRole(USER_ROLE.valueOf(
                authentication.getAuthorities().iterator().next().getAuthority()
        ));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private UsernamePasswordAuthenticationToken authenticate(String email) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername("seller_" + email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws SellerException {
        Seller savedSeller = sellerService.createSeller(seller);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws SellerException{
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellerByEmail(email);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws SellerException{
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellerByEmail(email);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException{
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status) throws SellerException{
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @PatchMapping
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller) throws SellerException{
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws SellerException{
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}
