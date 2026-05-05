package com.nirmalravidas.multivendor_ecommerce.controller;

import com.nirmalravidas.multivendor_ecommerce.exception.CartItemException;
import com.nirmalravidas.multivendor_ecommerce.exception.ProductException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.Cart;
import com.nirmalravidas.multivendor_ecommerce.model.CartItem;
import com.nirmalravidas.multivendor_ecommerce.model.Product;
import com.nirmalravidas.multivendor_ecommerce.model.User;
import com.nirmalravidas.multivendor_ecommerce.request.AddItemRequest;
import com.nirmalravidas.multivendor_ecommerce.response.ApiResponse;
import com.nirmalravidas.multivendor_ecommerce.service.CartItemService;
import com.nirmalravidas.multivendor_ecommerce.service.CartService;
import com.nirmalravidas.multivendor_ecommerce.service.ProductService;
import com.nirmalravidas.multivendor_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final CartItemService cartItemService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user);
        log.info("Cart - {}", cart.getUser().getEmail());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("Authorization") String jwt, @RequestBody AddItemRequest request) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Product product = productService.findProductById(request.getProductId());
        CartItem item = cartService.addCartItem(user, product, request.getSize(),request.getQuantity());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long cartItemId) throws CartItemException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse response = new ApiResponse("Item removed from cart", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long cartItemId, @RequestBody CartItem cartItem) throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);

        if (cartItem.getQuantity() <= 0) {
            throw new CartItemException("Quantity must be greater than 0");
        }

        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

}
