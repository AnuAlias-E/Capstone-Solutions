package com.training.ui;

import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.dto.OrderDTO;
import com.training.dto.request.OrderAddRequest;
import com.training.dto.request.OrderAddResponse;
import com.training.model.Order;
import com.training.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value="/add", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<OrderAddResponse> createOrder(@RequestBody OrderAddRequest orderRequest) {
        OrderDTO savedOrder = orderService.placeOrder(orderRequest);
        OrderAddResponse orderAddResponse = new OrderAddResponse();
        orderAddResponse.setStatusCode(HttpStatus.CREATED.value());
        orderAddResponse.setDescription("Order Placed Successfully for Order Id: "+savedOrder.getOrderId());
        orderAddResponse.setOrderDTO(savedOrder);
        return new ResponseEntity<>(orderAddResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}

