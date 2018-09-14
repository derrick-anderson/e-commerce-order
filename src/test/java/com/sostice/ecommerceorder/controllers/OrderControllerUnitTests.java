package com.sostice.ecommerceorder.controllers;

import com.sostice.ecommerceorder.domain.Order;
import com.sostice.ecommerceorder.service.OrderManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderManagementService orderManagementService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createOrder_HappyPath_Order() throws Exception{

        when(orderManagementService.createOrder(any())).thenReturn(getMockOrder());

        mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getOrderJson())
                )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.orderNumber", is(12345)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllOrders_HappyPath_OrderList() throws Exception {

        when(orderManagementService.getAllOrders()).thenReturn(getMockOrderList());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].orderNumber", is(12345)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getOneOrder_HappyPath_Order() throws Exception{

        when(orderManagementService.getSingleOrder(anyLong())).thenReturn(getMockOrder());

        mockMvc.perform(get("/orders/12345"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.orderNumber", is(12345)))
            .andDo(MockMvcResultHandlers.print());
    }

    //Helper Method that provides mock objects for class
    public Order getMockOrder(){
        Order mockOrder = new Order(1L, 1L);
        mockOrder.setOrderDate( LocalDate.of(2018,8,15));
        mockOrder.setOrderNumber(12345L);
        mockOrder.setTotalPrice(new BigDecimal("0.00"));

        return mockOrder;
    }

    public String getOrderJson(){
        return "{\"accountId\":1,\"shippingAddressId\":1}";
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