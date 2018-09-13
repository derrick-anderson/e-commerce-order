package com.sostice.ecommerceorder.controllers;

import com.sostice.ecommerceorder.service.LineManagementServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(LineController.class)
public class LineControllerUnitTests {

    @MockBean
    private LineManagementServices lineManagementServices;

    @InjectMocks
    private LineController lineController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNothing(){
        System.out.println();
    }

}
