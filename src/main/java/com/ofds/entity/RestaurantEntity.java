package com.ofds.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String owner_name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Double rating;
    
    private String image_url;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MenuItemEntity> menuItems;

    // One-to-Many to Carts
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<CartEntity> carts;

    // One-to-Many to Orders
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<OrdersEntity> orders;
}
