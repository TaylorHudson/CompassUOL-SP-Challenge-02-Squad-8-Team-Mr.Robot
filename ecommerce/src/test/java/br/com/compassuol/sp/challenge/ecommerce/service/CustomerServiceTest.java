package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.CustomerRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.InvalidCpfOrEmailException;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CustomerService service;

    @Test
    void findCustomerById() {
    }

    @Test
    void createCustomerSuccess() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();

        Customer customer =
                new Customer("John Doe", "12345678910", "john.doe@gmail.com", true);
        customer.setCustomerId(1);

        when(mapper.map(customerRequest, Customer.class)).thenReturn(customer);
        when(repository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO expectedResponse =
                new CustomerResponseDTO(1,"John Doe", "12345678910", "john.doe@gmail.com");

        when(mapper.map(customer, CustomerResponseDTO.class)).thenReturn(expectedResponse);

        CustomerResponseDTO response = service.createCustomer(customerRequest);

        assertEquals(expectedResponse, response);
        assertTrue(customer.isActive());
        assertEquals(1, response.getCustomerId());
        assertEquals("john.doe@gmail.com", response.getEmail());
        verify(mapper).map(customerRequest, Customer.class);
        verify(mapper).map(customer, CustomerResponseDTO.class);
        verify(repository).save(customer);
    }

    @Test
    void createCustomerInvalidCpfOrEmailException() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();

        Customer customer =
                new Customer("John Doe", "12345678910", "john.doe@gmail.com", true);
        customer.setCustomerId(1);

        when(repository.findByCpfOrEmail(any(), any())).thenReturn(Optional.of(customer));

        assertThrows(InvalidCpfOrEmailException.class, () -> service.createCustomer(customerRequest));
        verify(repository, times(0)).save(any(Customer.class));
    }

    @Test
    void updateCustomer() {
    }
}