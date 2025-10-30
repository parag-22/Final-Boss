package com.ofds.service;

import com.ofds.dto.OrdersDTO;
import com.ofds.dto.OrdersItemsDTO;
import com.ofds.entity.*;
import com.ofds.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    public OrdersDTO placeOrder(OrdersDTO dto) {
        OrdersEntity order = new OrdersEntity();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus("PLACED");
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setPaymentMethod(dto.getPaymentMethod());

        CustomerEntity customer = customerRepository.findById(dto.getCustomerId()).orElseThrow();
        RestaurantEntity restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElseThrow();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);

//        if (dto.getAgentId() != null) {
//            DeliveryAgentEntity agent = deliveryAgentRepository.findById(dto.getAgentId()).orElseThrow();
//            order.setAgent(agent);
//        }

        List<OrdersItemEntity> items = dto.getItems().stream().map(i -> {
            OrdersItemEntity item = new OrdersItemEntity();
            item.setMenuItem(i.getMenuItemId());
            item.setQuantity(i.getQuantity());
            item.setUnitPrice(i.getUnitPrice());
            item.setOrders(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        OrdersEntity saved = ordersRepository.save(order);

        dto.setId(saved.getId());
        dto.setOrderDate(saved.getOrderDate());
        dto.setStatus(saved.getStatus());
        return dto;
    }
}
