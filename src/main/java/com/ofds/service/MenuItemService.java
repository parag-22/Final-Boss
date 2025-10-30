package com.ofds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ofds.dto.MenuItemDTO;
import com.ofds.entity.MenuItemEntity;
import com.ofds.entity.RestaurantEntity;
import com.ofds.exception.DataNotFoundException;
import com.ofds.repository.MenuItemRepository;
import com.ofds.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemService {

	@Autowired
     MenuItemRepository menuItemRepository;
    
	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	ModelMapper modelMapper;
    

	//Display Menu Items For restaurant
	public ResponseEntity<List<MenuItemDTO>> getMenuItemsByRestaurantId(Integer restaurantId) throws DataNotFoundException {
		Optional<RestaurantEntity> restaurantOpt = restaurantRepository.findById(restaurantId);
	    if (restaurantOpt.isEmpty()) {
	    		        throw new DataNotFoundException("Restaurant not found with id: " + restaurantId);
	    		    }
	    else {
	    	
	    	List<MenuItemEntity> listEntity = restaurantOpt.get().getMenuItems();
         	List<MenuItemDTO> dtolst = listEntity.stream()
	    		    .map(itemEntity -> modelMapper.map(itemEntity, MenuItemDTO.class))
	    		    .toList();

	 	    return new ResponseEntity<>(dtolst, HttpStatus.OK);
	    }

	   
	}
	
	//Add new Item
    public ResponseEntity<MenuItemDTO> createMenuItem(Integer restaurantId, MenuItemDTO dto) throws DataNotFoundException {
        Optional<RestaurantEntity> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (restaurantOpt.isEmpty()) {
            throw new DataNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        MenuItemEntity item = modelMapper.map(dto, MenuItemEntity.class);
        item.setRestaurant(restaurantOpt.get());

        MenuItemEntity saved = menuItemRepository.save(item);
        MenuItemDTO responseDto = modelMapper.map(saved, MenuItemDTO.class);
        responseDto.setRestaurantId(restaurantId);
        responseDto.setName(dto.getName());
        responseDto.setPrice(dto.getPrice());
        
        log.info("Received DTO: " + responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //Update Existing Item
    public ResponseEntity<MenuItemDTO> updateMenuItem(Integer id, MenuItemDTO dto) throws DataNotFoundException {
        Optional<MenuItemEntity> itemOpt = menuItemRepository.findById(id);
        if (itemOpt.isEmpty()) {
            throw new DataNotFoundException("Menu item not found with id: " + id);
        }

        MenuItemEntity item = itemOpt.get();
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setImage_url(dto.getImage_url());

        MenuItemEntity updated = menuItemRepository.save(item);
        MenuItemDTO responseDto = modelMapper.map(updated, MenuItemDTO.class);
        responseDto.setRestaurantId(updated.getRestaurant().getId());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    
    //delete operation
    public ResponseEntity<Void> deleteMenuItem(Integer id) throws DataNotFoundException {
        Optional<MenuItemEntity> itemOpt = menuItemRepository.findById(id);
        if (itemOpt.isEmpty()) {
            throw new DataNotFoundException("Menu item not found with id: " + id);
        }

        menuItemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}