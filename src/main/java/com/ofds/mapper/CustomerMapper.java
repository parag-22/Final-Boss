package com.ofds.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class CustomerMapper {
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
