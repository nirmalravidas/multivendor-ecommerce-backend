package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.exception.CartItemException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.CartItem;

public interface CartItemService {
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
}
