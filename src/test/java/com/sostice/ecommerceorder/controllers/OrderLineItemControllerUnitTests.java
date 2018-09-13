package com.sostice.ecommerceorder.controllers;

import com.sostice.ecommerceorder.domain.Line;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderLineItemControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderManagementService orderManagementService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createLineForOrder_HappyPath_LineItem() throws Exception {

        mockMvc.perform(
                post("/orders/1/lines")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("")
        ).andExpect(status().isCreated());

    }

    @Test
    public void getAllLinesForOrder_HappyPath_ListLineItems() throws Exception {

        mockMvc.perform(get("/orders/1/lines"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneLineForOrder_HappyPath_LineItem() throws Exception {

        mockMvc.perform(get("/orders/1/lines/1"))
                .andExpect(status().isOk());
    }


}
