package com.ofds.repository;

import com.ofds.entity.OrdersItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemsRepository extends JpaRepository<OrdersItemEntity, Integer> {
}
