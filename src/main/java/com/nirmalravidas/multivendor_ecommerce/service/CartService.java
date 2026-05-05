package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.exception.ProductException;
import com.nirmalravidas.multivendor_ecommerce.model.Cart;
import com.nirmalravidas.multivendor_ecommerce.model.CartItem;
import com.nirmalravidas.multivendor_ecommerce.model.Product;
import com.nirmalravidas.multivendor_ecommerce.model.User;

public interface CartService {
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException;
    public Cart findUserCart(User user);
}
