package br.com.compassuol.sp.challenge.ecommerce.service;


import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Optional<Customer> findCustomerById(int id) {

        Optional<Customer> theCustomer = customerRepository.findById(id);

        return theCustomer;
    }

    public Customer createCustomer(Customer customer) {

        Customer theCustomer = customerRepository.save(customer);

        return theCustomer;
    }

    public Optional<Customer> updateCustomer(int id, Customer customer) {

        Optional<Customer> customerToUpdate = findCustomerById(id);

        if (customerToUpdate.isPresent()) {
            customer.setCustomerId(id);
            Customer updatedCostumer = customerRepository.save(customer);
            customerToUpdate = Optional.of(updatedCostumer);
        }

        return customerToUpdate;
    }
}