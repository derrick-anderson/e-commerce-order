package com.sostice.ecommerceorder.service;

import com.sostice.ecommerceorder.data.OrderRepository;
import com.sostice.ecommerceorder.domain.Order;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderManagementService {

    private OrderRepository orderRepository;

    public OrderManagementService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order getSingleOrder(long orderNumber) {
        return orderRepository.getOne(orderNumber);
    }

    public Order createOrder(Order orderIn) {
        return orderRepository.save(orderIn);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
