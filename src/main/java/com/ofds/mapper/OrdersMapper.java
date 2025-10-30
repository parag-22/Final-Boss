package com.ofds.mapper;

import com.ofds.dto.OrdersDTO;
import com.ofds.dto.OrdersItemsDTO;
import com.ofds.entity.OrdersEntity;
import com.ofds.entity.OrdersItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersMapper {

    public OrdersDTO toDTO(OrdersEntity entity) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus());
        dto.setDeliveryAddress(entity.getDeliveryAddress());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setRestaurantId(entity.getRestaurant().getId());
        dto.setAgentId(entity.getAgent() != null ? entity.getAgent().getId() : null);

        List<OrdersItemsDTO> items = entity.getItems().stream().map(item -> {
            OrdersItemsDTO itemDTO = new OrdersItemsDTO();
            itemDTO.setMenuItemId(item.getMenuItem());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setUnitPrice(item.getUnitPrice());
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}
