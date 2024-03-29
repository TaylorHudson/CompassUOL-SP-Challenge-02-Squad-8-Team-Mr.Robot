package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.CustomerRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.InvalidCpfOrEmailException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import br.com.compassuol.sp.challenge.ecommerce.utils.CustomerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private CustomerService service;


    @Test
    void findCustomerByIdSuccess() {

        Customer customer = CustomerUtil.createCustomerDefault();

        CustomerResponseDTO expectedResponse = CustomerUtil.createExpectedResponseDefault();

       when(repository.findById(any())).thenReturn(Optional.of(customer));

       CustomerResponseDTO response = service.findCustomerById(1);

       assertAll("response",
                () -> assertEquals(expectedResponse.getEmail(), response.getEmail()),
                () -> assertEquals(expectedResponse.getCpf(), response.getCpf()),
                () -> assertEquals(1, response.getCustomerId())
        );
       verify(repository).findById(1);
    }

    @Test
    void findCustomerByIdErrorResourceNotFound() {

        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findCustomerById(1));
        verify(repository).findById(1);
    }

    @Test
    void createCustomerSuccess() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        Customer customer = CustomerUtil.createCustomerDefault();

        when(repository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO expectedResponse = CustomerUtil.createExpectedResponseDefault();
        CustomerResponseDTO response = service.createCustomer(customerRequest);

        assertAll("response",
                () -> assertEquals(expectedResponse.getCpf(), response.getCpf()),
                () -> assertEquals(expectedResponse.getEmail(), response.getEmail()),
                () -> assertTrue(customer.isActive()),
                () -> assertEquals(1, response.getCustomerId())
        );
        verify(repository).save(any(Customer.class));
    }

    @Test
    void createCustomerInvalidCpfOrEmailException() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();

        Customer customer = CustomerUtil.createCustomerDefault();

        when(repository.findByCpfOrEmail(any(), any())).thenReturn(Optional.of(customer));

        assertThrows(InvalidCpfOrEmailException.class, () -> service.createCustomer(customerRequest));
        verify(repository, times(0)).save(any(Customer.class));
    }

    @Test
    void updateCustomerSuccess() {
        CustomerRequestDTO customerRequest =
                new CustomerRequestDTO("JohnDoe","12345678910","john.doe@gmail.com");

        Customer customer = CustomerUtil.createCustomerDefault();

        CustomerResponseDTO expectedResponse = CustomerUtil.createExpectedResponseDefault();

        when(repository.findById(anyInt())).thenReturn(Optional.of(customer));
        when(repository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO response = service.updateCustomer(1, customerRequest);

        assertAll("response",
                () -> assertEquals(expectedResponse.getCpf(), response.getCpf()),
                () -> assertEquals(expectedResponse.getEmail(), response.getEmail()),
                () -> assertEquals(1, response.getCustomerId())
        );
        verify(repository).findById(anyInt());
        verify(repository).save(any(Customer.class));
    }

    @Test
    void updateCustomerErrorResourceNotFound() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findCustomerById(1));
        verify(repository).findById(1);
    }

}