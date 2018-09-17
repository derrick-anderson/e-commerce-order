package com.sostice.ecommerceorder.controllers;

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
        return orderManagementService.getOneOrder(orderNumber);
    }

    @DeleteMapping("/orders/{orderNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderNumber") Long orderNumber){
        orderManagementService.deleteOrder(orderNumber);
    }

    @PutMapping("/orders/{orderNumber}")
    public Order updateOrder(@PathVariable("orderNumber") Long orderNumber, Order orderUpdateInfo){
        return orderManagementService.updateOrder(orderNumber, orderUpdateInfo);
    }
}
