package com.javaproject.springshop.service.order;

import java.util.List;

import com.javaproject.springshop.dto.OrderDto;
import com.javaproject.springshop.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

}
