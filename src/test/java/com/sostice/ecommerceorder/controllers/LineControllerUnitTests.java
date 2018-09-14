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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        String lineJson = "{\"quantity\": 20, \"unitPrice\": 150.00, \"productId\": 5, \"orderNumber\": 12345}";
        mockMvc.perform(post("/orders/12345/lines")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(lineJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.lineItemId", is(15)))
                .andExpect(jsonPath("$.quantity", is(20)))
                .andExpect(jsonPath("$.unitPrice", is(150.00)))
                .andExpect(jsonPath("$.totalPrice", is(3000.00)))
                .andExpect(jsonPath("$.productId", is(nullValue())))
                .andExpect(jsonPath("$.orderNumber", is(12345)))
                .andExpect(jsonPath("$.shipmentId", is(nullValue())))
        ;
    }

    @Test
    public void getAllLinesForOrder_HappyPath() throws Exception{
        when(lineManagementServices.getAllLinesForOrder(12345L)).thenReturn(getMockLineList());

        mockMvc.perform(get("/orders/12345/lines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[1].lineItemId", is(16)))
                .andExpect(jsonPath("$[2].orderNumber", is(12345)))
                .andExpect(jsonPath("$[3].totalPrice", is(3000.00)))
                .andExpect(jsonPath("$[4].shipmentId", is(nullValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getOneLineById_HappyPath() throws Exception{
        when(lineManagementServices.getOneLineById(15L)).thenReturn(getSavedMockLine(15L));

        mockMvc.perform(get("/orders/12345/lines/15"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.lineItemId", is(15)))
                .andExpect(jsonPath("$.quantity", is(20)))
                .andExpect(jsonPath("$.unitPrice", is(150.00)))
                .andExpect(jsonPath("$.totalPrice", is(3000.00)))
                .andExpect(jsonPath("$.shipmentId", is(nullValue())))
                .andExpect(jsonPath("$.productId", is(nullValue())))
                .andExpect(jsonPath("$.shipmentId", is(nullValue())))
                .andExpect(jsonPath("$.orderNumber", is(12345)));
    }

    private List<Line> getMockLineList() {
        return new ArrayList<Line>(){{
            add(getSavedMockLine(15L));
            add(getSavedMockLine(16L));
            add(getSavedMockLine(17L));
            add(getSavedMockLine(18L));
            add(getSavedMockLine(19L));
        }};
    }

    //Helper Methods
    private Line getSavedMockLine(Long id){
        //Line mockLine = new Line(20, new BigDecimal("150.00"), null, 12345L);
        Line mockLine = new Line();
        mockLine.setQuantity(20);
        mockLine.setUnitPrice(new BigDecimal("150.00"));
        mockLine.setProductId(null);
        mockLine.setOrderNumber(12345L);
        mockLine.setLineItemId(id);
        return mockLine;
    }

}
