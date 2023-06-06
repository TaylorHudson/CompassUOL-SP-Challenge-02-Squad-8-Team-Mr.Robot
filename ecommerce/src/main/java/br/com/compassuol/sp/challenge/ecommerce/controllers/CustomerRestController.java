package br.com.compassuol.sp.challenge.ecommerce.controllers;


import br.com.compassuol.sp.challenge.ecommerce.dto.request.CustomerRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> findCustomerById(@PathVariable int id){
        var customerDTO = customerService.findCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable int id,
            @Valid @RequestBody CustomerRequestDTO customer){
        var updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

}
