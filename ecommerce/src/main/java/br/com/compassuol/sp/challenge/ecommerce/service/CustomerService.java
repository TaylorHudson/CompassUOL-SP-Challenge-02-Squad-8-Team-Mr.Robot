package br.com.compassuol.sp.challenge.ecommerce.service;


import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find customer with id - " + id));
    }

    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);
        createdCustomer.setActive(true);
        return createdCustomer;
    }

    public Customer updateCustomer(int id, Customer customer) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The id supplied must be from a customer that is already created"));

        customer.setCustomerId(id);
        return customerRepository.save(customer);
    }

}