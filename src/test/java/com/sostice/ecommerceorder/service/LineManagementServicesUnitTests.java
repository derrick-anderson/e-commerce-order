package com.sostice.ecommerceorder.service;

import com.sostice.ecommerceorder.data.LineRepository;
import com.sostice.ecommerceorder.domain.Line;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class LineManagementServicesUnitTests {

    @MockBean
    private LineRepository lineRepository;

    @InjectMocks
    private LineManagementServices lineManagementServices;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllLinesForOrder_HappyPath(){

        when(lineRepository.findAllByOrderId(anyLong())).thenReturn(getMockLineList());

        List<Line> allLines = lineManagementServices.getAllLinesForOrder(12345L);

        assertThat(allLines.size(), is(6));
        assertThat(allLines.get(1).getProductId(), is(5L));
        assertThat(allLines.get(1).getQuantity(), is(20));
        assertThat(allLines.get(1).getUnitPrice(), is(new BigDecimal("150.00")));
        assertThat(allLines.get(1).getTotalPrice(), is(new BigDecimal("3000.00")));
        assertThat(allLines.get(1).getShipmentId(), is(67890L));
        assertThat(allLines.get(1).getOrderId(), is(12345L));
    }

    @Test
    public void getOneLineById_HappyPath(){

        when(lineRepository.getOne(15L)).thenReturn(getMockLine(15L));

        Line foundLine = lineManagementServices.getOneLineById(15L);

        assertThat(foundLine.getLineItemId(), is(15L));
        assertThat(foundLine.getProductId(), is(5L));
        assertThat(foundLine.getQuantity(), is(20));
        assertThat(foundLine.getUnitPrice(), is(new BigDecimal("150.00")));
        assertThat(foundLine.getTotalPrice(), is(new BigDecimal("3000.00")));
        assertThat(foundLine.getShipmentId(), is(67890L));
        assertThat(foundLine.getOrderId(), is(12345L));
    }

    @Test
    public void saveLineItem_HappyPath(){

        when(lineRepository.save(any(Line.class))).thenReturn(getMockSavedLine());

        Line savedLine = lineManagementServices.saveLine(getMockLineToSave());

        assertThat(savedLine.getLineItemId(), is(15L));
        assertThat(savedLine.getProductId(), is(5L));
        assertThat(savedLine.getQuantity(), is(10));
        assertThat(savedLine.getUnitPrice(), is(new BigDecimal("80.00")));
        assertThat(savedLine.getTotalPrice(), is(new BigDecimal("800.00")));
        assertThat(savedLine.getShipmentId(), is(nullValue()));
        assertThat(savedLine.getOrderId(), is(12345L));

    }


    //Convenience Methods
    private Line getMockLine(Long lineId){
        Line mockLine = new Line(20, new BigDecimal("150.00"), 5L ,12345L );
        mockLine.setLineItemId(lineId);
        mockLine.setShipmentId(67890L);
        return mockLine;
    }

    private Line getMockSavedLine(){
        Line mockLine = new Line(10, new BigDecimal("80.00"), 5L ,12345L );
        mockLine.setLineItemId(15L);
        return mockLine;
    }

    private Line getMockLineToSave(){
        return new Line(10, new BigDecimal("80.00"), 5L, 12345L);

    }

    private List<Line> getMockLineList(){
        return new ArrayList<Line>(){{
            add(getMockLine(15L));
            add(getMockLine(16L));
            add(getMockLine(17L));
            add(getMockLine(18L));
            add(getMockLine(19L));
            add(getMockLine(20L));
        }};
    }
}