package com.javaproject.springshop.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.javaproject.springshop.exceptions.ResourceNotFoundException;
import com.javaproject.springshop.model.Cart;
import com.javaproject.springshop.model.CartItem;
import com.javaproject.springshop.model.Product;
import com.javaproject.springshop.repository.CartItemRepository;
import com.javaproject.springshop.repository.CartRepository;
import com.javaproject.springshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRespository;
    private final ICartService cartService;
    private final IProductService productService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. get cart
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRespository.save(cartItem);
        cartRepository.save(cart);

    }
    
    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().ifPresent(item -> {
            item.setQuantity(quantity);
            item.setUnitPrice(item.getProduct().getPrice());
            item.setTotalPrice();
        });
        BigDecimal totalAmount = cart.getItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

}
