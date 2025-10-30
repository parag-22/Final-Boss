package com.ofds.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ofds.entity.MenuItemEntity;
import com.ofds.entity.RestaurantEntity;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {

	Optional<MenuItemEntity> findById(Integer id);
	
	
}
