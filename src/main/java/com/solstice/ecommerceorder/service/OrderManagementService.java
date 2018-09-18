package com.solstice.ecommerceorder.service;

import com.solstice.ecommerceorder.data.AccountFeignProxy;
import com.solstice.ecommerceorder.data.LineRepository;
import com.solstice.ecommerceorder.data.OrderRepository;
import com.solstice.ecommerceorder.domain.Order;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderManagementService {

    private OrderRepository orderRepository;

    private LineRepository lineRepository;

    private AccountFeignProxy accountProxy;

    public OrderManagementService(OrderRepository orderRepository, LineRepository lineRepository, AccountFeignProxy accountProxy){
        this.orderRepository = orderRepository;
        this.lineRepository = lineRepository;
        this.accountProxy = accountProxy;
    }

    public Order getOneOrder(long orderNumber) {
        Optional<Order> foundOrder = orderRepository.findById(orderNumber);
        if(foundOrder.isPresent()) {
            return addDetails(foundOrder.get());
        }
        else return null;
    }

    private Order addDetails(Order orderIn){
        if(orderIn.getAccountId()!= null){
            orderIn.setAccount(accountProxy.getAccount(orderIn.getAccountId()));
        }
        if(orderIn.getShippingAddressId() != null){
            orderIn.setShippingAddress(accountProxy.getAddress(orderIn.getAccountId(), orderIn.getShippingAddressId()));
        }
        if(lineRepository.findAllByOrderNumber(orderIn.getOrderNumber()) != null){
            orderIn.setLineItems(lineRepository.findAllByOrderNumber(orderIn.getOrderNumber()));
        }
        return orderIn;
    }

    public Order createOrder(Order orderIn) {
        String checkedAccount = accountProxy.getAccount(orderIn.getAccountId());
        if(checkedAccount != null){
            return orderRepository.save(orderIn);
        }
        else throw new EntityNotFoundException();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> addDetails(order)).collect(Collectors.toList());
    }

    public void deleteOrder(Long orderNumber) {
        if(orderRepository.findById(orderNumber).isPresent()){
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
