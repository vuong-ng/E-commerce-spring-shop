package com.javaproject.springshop.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaproject.springshop.exceptions.ResourceNotFoundException;
import com.javaproject.springshop.model.Cart;
import com.javaproject.springshop.model.User;
import com.javaproject.springshop.response.ApiResponse;
import com.javaproject.springshop.service.cart.ICartItemService;
import com.javaproject.springshop.service.cart.ICartService;
import com.javaproject.springshop.service.user.IUserService;
import io.jsonwebtoken.JwtException;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItem(
            @RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);
            System.out.println(cart.getId() + " " + cart.getUser().getId());

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add item successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }
    

    @PostMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove item successsfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId,
            @RequestParam Integer quantity) {
                try {
                    cartItemService.updateItemQuantity(cartId, itemId, quantity);
                    return ResponseEntity.ok(new ApiResponse("Update quantity successfullt", null));
                } catch (Exception e) {
                    return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }

    }

}
  