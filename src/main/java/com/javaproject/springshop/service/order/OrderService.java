package com.javaproject.springshop.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.javaproject.springshop.dto.OrderDto;
import com.javaproject.springshop.enums.OrderStatus;
import com.javaproject.springshop.exceptions.ResourceNotFoundException;
import com.javaproject.springshop.model.Cart;
import com.javaproject.springshop.model.Order;
import com.javaproject.springshop.model.OrderItem;
import com.javaproject.springshop.model.Product;
import com.javaproject.springshop.repository.OrderRepository;
import com.javaproject.springshop.repository.ProductRepository;
import com.javaproject.springshop.service.cart.ICartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICartService cartService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = CreateOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        return order;
    }
    
    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    // takes items in cart set the corresponding order for the items
    private List<OrderItem> CreateOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(item -> {
            Product product = item.getProduct();
            product.setInventory(product.getInventory() - item.getQuantity());
            productRepository.save(product);
            return new OrderItem(order, product, item.getQuantity(), item.getUnitPrice());
        }).toList();
    }
    
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this :: convertToDto).toList();
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
