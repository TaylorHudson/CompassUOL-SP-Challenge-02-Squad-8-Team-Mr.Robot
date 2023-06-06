package br.com.compassuol.sp.challenge.ecommerce.controllers;


import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{id}")
    public Customer findCustomerById(@Valid @PathVariable int id){

        Optional<Customer> result = customerService.findCustomerById(id);

        Customer theCustomer = null;

        if(result.isPresent()){
            theCustomer = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id-"+id);
        }
        return theCustomer;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer){

        Customer dbCustomer = customerService.createCustomer(customer);

        return dbCustomer;
    }

    @PutMapping("/customers/{id}")
    public Optional<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){

        Optional<Customer> customerUpdated = customerService.updateCustomer(id,customer);

        return  customerUpdated;

    }
}
