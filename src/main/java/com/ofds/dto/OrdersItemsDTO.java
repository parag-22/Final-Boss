package com.ofds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersItemsDTO {
    private Integer menuItemId;
    private Integer quantity;
    private Double unitPrice;
}
