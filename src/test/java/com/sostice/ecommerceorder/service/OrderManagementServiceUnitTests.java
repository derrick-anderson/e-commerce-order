package com.sostice.ecommerceorder.service;

import com.sostice.ecommerceorder.data.OrderRepository;
import com.sostice.ecommerceorder.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderManagementServiceUnitTests {

    @MockBean
    private OrderRepository orderRepository;

    private OrderManagementService orderManagementService;

    @Before
    public void setup(){
        orderManagementService = new OrderManagementService(orderRepository);
    }

    @Test
    public void testGetSingleOrder_HappyPath(){
        when(orderRepository.getOne(anyLong())).thenReturn(getMockOrder());

        Order foundOrder = orderManagementService.getSingleOrder(12345L);
        assertThat(foundOrder.getAccountId(), is(1L));
        assertThat(foundOrder.getShippingAddressId(), is(1L));
        assertThat(foundOrder.getOrderDate().toString(), is("2018-08-15"));

    }

    @Test
    public void testGetAllOrders_HappyPath(){

        when(orderRepository.findAll()).thenReturn(getMockOrderList());

        List<Order> foundOrders = orderManagementService.getAllOrders();
        assertThat(foundOrders.size(), is(4));
        assertThat(foundOrders.get(3).getOrderNumber(), is(12345L));
    }

    @Test
    public void testSaveOrCreateOrder_HappyPath(){
        when(orderRepository.save(any(Order.class))).thenReturn(getMockOrder());

        Order savedOrder = orderManagementService.createOrder(getOrderToSave());

        assertThat(savedOrder.getOrderNumber(), is(12345L));
        assertThat(savedOrder.getOrderDate().toString(), is("2018-08-15"));


    }

    //Convenience Methods
    public Order getMockOrder(){
        Order mockOrder = new Order(1L, 1L);
        mockOrder.setOrderDate( LocalDate.of(2018,8,15));
        mockOrder.setOrderNumber(12345L);
        mockOrder.setTotalPrice(new BigDecimal("0.00"));

        return mockOrder;
    }

    public Order getOrderToSave(){
        return new Order( 1L, 1L);
    }
    public List<Order> getMockOrderList(){
        return new ArrayList<Order>(){{
            add(getMockOrder());
            add(getMockOrder());
            add(getMockOrder());
            add(getMockOrder());
        }};
    }
}
