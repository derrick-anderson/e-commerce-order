package com.solstice.ecommerceorder.service;

import com.solstice.ecommerceorder.data.LineRepository;
import com.solstice.ecommerceorder.domain.Line;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = LineManagementServices.class)
public class LineManagementServicesUnitTests {

    @MockBean
    private LineRepository lineRepository;

    @Autowired
    private LineManagementServices lineManagementServices;

    @Test
    public void getAllLinesForOrder_HappyPath(){
        when(lineRepository.findAllByOrderNumber(anyLong())).thenReturn(getMockLineList());

        List<Line> allLines = lineManagementServices.getAllLinesForOrder(12345L);

        assertThat(allLines.size(), is(6));
        assertThat(allLines.get(1).getProductId(), is(5L));
        assertThat(allLines.get(1).getQuantity(), is(20));
        assertThat(allLines.get(1).getUnitPrice(), is(new BigDecimal("150.00")));
        assertThat(allLines.get(1).getTotalPrice(), is(new BigDecimal("3000.00")));
        assertThat(allLines.get(1).getShipmentId(), is(67890L));
        assertThat(allLines.get(1).getOrderNumber(), is(12345L));
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
        assertThat(foundLine.getOrderNumber(), is(12345L));
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
        assertThat(savedLine.getOrderNumber(), is(12345L));

    }

    @Test
    public void addLineItemToOrder_HappyPath(){
        when(lineRepository.findAllByOrderNumber(12345L)).thenReturn(getMockLineList());

        Line savedLine = lineManagementServices.addLineToOrder(getMockLineToSave().getOrderNumber(), getMockLineToSave());
    }

    @Test
    public void deleteLineItem_HappyPath(){
        when(lineRepository.getOne(15L)).thenReturn(getMockLine(15L));

        lineManagementServices.deleteLine(15L);

        verify(lineRepository, times(1)).getOne(15L);
        verify(lineRepository, times(1)).deleteById(15L);
    }

    @Test
    public void updateLineItem_HappyPath(){
        when(lineRepository.getOne(15L)).thenReturn(getMockLine(15L));

        Line lineToUpdate = new Line();
        lineToUpdate.setQuantity(10);

        Line updatedLine = lineManagementServices.updateLine(15L, lineToUpdate);
        assertThat(updatedLine.getQuantity(), is(10));
        assertThat(updatedLine.getTotalPrice(), is(new BigDecimal("1500.00")));

        verify(lineRepository, times(1)).save(any(Line.class));
    }

    //Convenience Methods
    private static Line getMockLine(Long lineId){
        Line mockLine = new Line(20, new BigDecimal("150.00"), 5L ,12345L );
        mockLine.setLineItemId(lineId);
        mockLine.setShipmentId(67890L);
        return mockLine;
    }

    private static Line getMockSavedLine(){
        Line mockLine = new Line(10, new BigDecimal("80.00"), 5L ,12345L );
        mockLine.setLineItemId(15L);
        return mockLine;
    }

    private static Line getMockLineToSave(){
        return new Line(10, new BigDecimal("80.00"), 5L, 12345L);

    }

    private static List<Line> getMockLineList(){
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
