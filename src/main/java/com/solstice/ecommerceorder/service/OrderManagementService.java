package com.solstice.ecommerceorder.service;

import com.solstice.ecommerceorder.data.AccountFeignProxy;
import com.solstice.ecommerceorder.data.OrderRepository;
import com.solstice.ecommerceorder.domain.Order;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderManagementService {

    private OrderRepository orderRepository;

    private AccountFeignProxy accountProxy;

    public OrderManagementService(OrderRepository orderRepository, AccountFeignProxy accountProxy){
        this.orderRepository = orderRepository;
        this.accountProxy = accountProxy;
    }

    public Order getOneOrder(long orderNumber) {
        Optional<Order> foundOrder = orderRepository.findById(orderNumber);
        if(foundOrder.isPresent()) {
            Order thisOrder = foundOrder.get();
            if(thisOrder.getAccountId()!= null){
                thisOrder.setAccount(accountProxy.getAccount(thisOrder.getAccountId()));
            }
            return foundOrder.get();
        }
        else return null;
    }

    public Order createOrder(Order orderIn) {
        String checkedAccount = accountProxy.getAccount(orderIn.getAccountId());
        if(checkedAccount != null){
            return orderRepository.save(orderIn);
        }
        else throw new EntityNotFoundException();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long orderNumber) {
        if(getOneOrder(orderNumber) != null){
            orderRepository.deleteById(orderNumber);
        }else throw new EntityNotFoundException();
    }

    public Order updateOrder(Long orderNumber, Order orderUpdateInfo) {
        if(getOneOrder(orderNumber) != null){
            Order savedOrder = getOneOrder(orderNumber);

            if(orderUpdateInfo.getAccountId() != null
                    & accountProxy.getAccount(orderUpdateInfo.getAccountId()) != null){
                savedOrder.setAccountId(orderUpdateInfo.getAccountId());
            }else throw new EntityNotFoundException();

            if(orderUpdateInfo.getShippingAddressId() != null){
                savedOrder.setShippingAddressId(orderUpdateInfo.getShippingAddressId());
            }
            orderRepository.save(savedOrder);
            return savedOrder;
        }else throw new EntityNotFoundException();
    }
}
