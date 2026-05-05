package com.nirmalravidas.multivendor_ecommerce.service.impl;

import com.nirmalravidas.multivendor_ecommerce.exception.ProductException;
import com.nirmalravidas.multivendor_ecommerce.model.Cart;
import com.nirmalravidas.multivendor_ecommerce.model.CartItem;
import com.nirmalravidas.multivendor_ecommerce.model.Product;
import com.nirmalravidas.multivendor_ecommerce.model.User;
import com.nirmalravidas.multivendor_ecommerce.repository.CartItemRepository;
import com.nirmalravidas.multivendor_ecommerce.repository.CartRepository;
import com.nirmalravidas.multivendor_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException {
        Cart cart = findUserCart(user);
        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);
        if (isPresent == null){
            CartItem cartItem = new CartItem();

            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSellingPrice(quantity*product.getSellingPrice());
            cartItem.setMrpPrice(quantity*product.getMrpPrice());
            cartItem.setSize(size);

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {

        Cart cart =	cartRepository.findByUserId(user.getId());

        int totalPrice=0;
        int totalItem=0;
        for(CartItem cartsItem : cart.getCartItems()) {
            totalPrice =  totalPrice + cartsItem.getMrpPrice();
            totalItem+=cartsItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setTotalSellingPrice(cart.getTotalSellingPrice());
        cart.setTotalItem(totalItem);

        return cartRepository.save(cart);
    }
}
