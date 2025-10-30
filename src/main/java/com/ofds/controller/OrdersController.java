package com.ofds.controller;

import com.ofds.dto.OrdersDTO;
import com.ofds.service.OrdersService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrdersController {

	@Autowired
     OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrdersDTO> placeOrder(@RequestBody OrdersDTO dto) {
        OrdersDTO placed = ordersService.placeOrder(dto);
        return ResponseEntity.ok(placed);
    }
}
