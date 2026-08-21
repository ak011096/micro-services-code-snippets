package ecom_order_service.order_service.controllers;

import ecom_order_service.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;



    @PostMapping("/{productId}")
    public String placeOrder(@PathVariable String productId){
        return orderService.placeOrder(productId);
    }

}
