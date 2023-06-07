package br.com.compassuol.sp.challenge.ecommerce.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.CustomerRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper mapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    public CustomerResponseDTO findCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find customer with id - " + id));

        return mapper.map(customer, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customer) {

        Customer createdCustomer = mapper.map(customer,Customer.class);

        createdCustomer = customerRepository.save(createdCustomer);

        createdCustomer.setActive(true);

        return mapper.map(createdCustomer,CustomerResponseDTO.class);
    }

    public CustomerResponseDTO updateCustomer(int id, CustomerRequestDTO request) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The id supplied must be from a customer that is already created"));

        Customer customer = mapper.map(request, Customer.class);
        customer.setCustomerId(id);
        Customer updatedCustomer = customerRepository.save(customer);
        return mapper.map(updatedCustomer, CustomerResponseDTO.class);
    }

}