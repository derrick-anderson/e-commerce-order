package com.sostice.ecommerceorder.service;

import com.sostice.ecommerceorder.domain.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagementService {


    public Order getSingleOrder(long orderNumber) {
        return new Order();
    }

    public Order saveOrCreateOrder(Order orderIn) {
        return null;
    }

    public List<Order> getAllOrders() {
        return null;
    }
}
