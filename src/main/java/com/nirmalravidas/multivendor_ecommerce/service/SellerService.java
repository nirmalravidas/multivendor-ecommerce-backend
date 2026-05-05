package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.enums.AccountStatus;
import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.model.Seller;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt) throws SellerException;
    Seller createSeller(Seller seller) throws SellerException;
    List<Seller> getAllSellers(AccountStatus accountStatus) throws SellerException;
    Seller getSellerById(Long id) throws SellerException;
    Seller updateSeller(Long id, Seller seller) throws SellerException;
    void deleteSeller(Long id) throws SellerException;
    Seller getSellerByEmail(String email) throws SellerException;
    Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException;
}
