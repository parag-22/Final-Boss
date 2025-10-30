package com.ofds.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    private Integer id;
    private String name;
    private Double price;
    private String image_url;
    private Integer restaurantId;
}

