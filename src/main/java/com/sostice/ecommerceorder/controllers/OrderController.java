package com.sostice.ecommerceorder.controllers;

        import com.sostice.ecommerceorder.domain.Line;
        import com.sostice.ecommerceorder.domain.Order;
        import com.sostice.ecommerceorder.service.OrderManagementService;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
public class OrderController {

    private OrderManagementService orderManagementService;

    private OrderController(OrderManagementService orderManagementService){
        this.orderManagementService = orderManagementService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(Order orderIn){
        return orderManagementService.createOrder(orderIn);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderManagementService.getAllOrders();
    }

    @GetMapping("/orders/{orderNumber}")
    public Order getOneOrder(@PathVariable("orderNumber") Long orderNumber){
        return orderManagementService.getSingleOrder(orderNumber);
    }

//    @PostMapping("/orders/{orderNumber}/lines")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Line createLineForOrder(@PathVariable("orderNumber") Long orderNumber, Line orderLine){
//        return null;
//    }
//
//    @GetMapping("/orders/{orderNumber}/lines")
//    public List<Line> getAllLinesForOrder(@PathVariable("orderNumber") Long orderNumber){
//        return null;
//    }
//
//    @GetMapping("/orders/{orderNumber}/lines/{lineId}")
//    public Line getOneLineForOrder(@PathVariable("orderNumber") Long orderNumber, @PathVariable("lineId") Long lineId){
//        return null;
//    }

}
