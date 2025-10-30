package com.ofds.controller;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.ofds.dto.CustomerDTO;
import com.ofds.dto.LoginDTO;
import com.ofds.exception.NoDataFoundException;
import com.ofds.exception.RecordAlreadyFoundException;
import com.ofds.service.CustomerService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping ("/api/auth")
public class CustomerController 
{

	@Autowired
	CustomerService custServiceObj;

    @GetMapping("/getCustomerData")
    public ResponseEntity<List<CustomerDTO>> getCustomerData() throws NoDataFoundException 
    {
        return custServiceObj.getCustomerData();
    }
    
//    for testing in postman:
    @GetMapping ("/hello")
	public String hello() {
		System.out.println("Inside hello() of MyController...");
		return "Hello, you are an authenticated user..";
	}

    @PostMapping("/insertCustomerData")
    public ResponseEntity<CustomerDTO> insertCustomerData(@Valid @RequestBody CustomerDTO customerObj) throws RecordAlreadyFoundException 
    {
        return custServiceObj.insertCustomerData(customerObj);
    }
    

    
  

}

