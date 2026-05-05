package com.nirmalravidas.multivendor_ecommerce.service.impl;

import com.nirmalravidas.multivendor_ecommerce.exception.CartItemException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.CartItem;
import com.nirmalravidas.multivendor_ecommerce.model.User;
import com.nirmalravidas.multivendor_ecommerce.repository.CartItemRepository;
import com.nirmalravidas.multivendor_ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item=findCartItemById(id);
        User cartItemUser=item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {

            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);
        }
        else {
            throw new CartItemException("You can't update  another users cart_item");
        }
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException{
        CartItem cartItem=findCartItemById(cartItemId);

        User cartItemUser=cartItem.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {
            cartItemRepository.deleteById(cartItem.getId());
        }
        else {
            throw new UserException("you can't remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException{
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id : "+cartItemId);
    }
}
