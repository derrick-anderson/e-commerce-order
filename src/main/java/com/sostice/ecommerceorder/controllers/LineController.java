package com.sostice.ecommerceorder.controllers;

import com.sostice.ecommerceorder.domain.Line;
import com.sostice.ecommerceorder.service.LineManagementServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LineController {

    private LineManagementServices lineManagementServices;

    private LineController(LineManagementServices lineManagementServices){
        this.lineManagementServices = lineManagementServices;
    }

    @PostMapping("/orders/{orderNumber}/lines")
    @ResponseStatus(HttpStatus.CREATED)
    public Line createLineForOrder(@PathVariable("orderNumber") Long orderNumber, Line orderLine){
        return null;
    }

    @GetMapping("/orders/{orderNumber}/lines")
    public List<Line> getAllLinesForOrder(@PathVariable("orderNumber") Long orderNumber){
        return null;
    }

    @GetMapping("/orders/{orderNumber}/lines/{lineId}")
    public Line getOneLineForOrder(@PathVariable("orderNumber") Long orderNumber, @PathVariable("lineId") Long lineId){
        return null;
    }

}
