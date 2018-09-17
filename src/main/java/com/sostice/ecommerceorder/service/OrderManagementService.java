package com.sostice.ecommerceorder.service;

import com.sostice.ecommerceorder.data.OrderRepository;
import com.sostice.ecommerceorder.domain.Order;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderManagementService {

    private OrderRepository orderRepository;

    public OrderManagementService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order getOneOrder(long orderNumber) {
        return orderRepository.getOne(orderNumber);
    }

    public Order createOrder(Order orderIn) {
        return orderRepository.save(orderIn);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long orderNumber) {
        if(orderRepository.getOne(orderNumber) != null){
            orderRepository.deleteById(orderNumber);
        }else throw new EntityNotFoundException();
    }

    public Order updateOrder(Long orderNumber, Order orderUpdateInfo) {
        if(getOneOrder(orderNumber) != null){
            Order savedOrder = getOneOrder(orderNumber);

            if(orderUpdateInfo.getAccountId() != null){
                savedOrder.setAccountId(orderUpdateInfo.getAccountId());
            }
            if(orderUpdateInfo.getShippingAddressId() != null){
                savedOrder.setShippingAddressId(orderUpdateInfo.getShippingAddressId());
            }
            orderRepository.save(savedOrder);
            return savedOrder;
        }else throw new EntityNotFoundException();
    }
}
