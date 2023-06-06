package br.com.compassuol.sp.challenge.ecommerce.service;


import br.com.compassuol.sp.challenge.ecommerce.dto.request.CustomerRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);
        createdCustomer.setActive(true);
        return createdCustomer;
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