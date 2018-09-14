package com.sostice.ecommerceorder.controllers;

import com.sostice.ecommerceorder.domain.Line;
import com.sostice.ecommerceorder.service.LineManagementServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LineController.class)
public class LineControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LineManagementServices lineManagementServices;

    @Autowired
    private LineController lineController;

    @Test
    public void createLineItemForOrder_HappyPath() throws Exception {

        when(lineManagementServices.saveLine(any(Line.class))).thenReturn(getSavedMockLine(15L));

        mockMvc.perform(post("/orders/12345/lines")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.lineItemId", is(15)))
                .andExpect(jsonPath("$.quantity", is(20)))
                .andExpect(jsonPath("$.unitPrice", is(150.00)))
                .andExpect(jsonPath("$.totalPrice", is(3000.00)))
                .andExpect(jsonPath("$.productId", is(nullValue())))
                .andExpect(jsonPath("$.orderId", is(12345)))
                .andExpect(jsonPath("$.shipmentId", is(nullValue())))
        ;
    }

    //Helper Methods
    private Line getSavedMockLine(Long id){
        Line mockLine = new Line(20, new BigDecimal("150.00"), null, 12345L);
        mockLine.setLineItemId(id);
        return mockLine;
    }

}
