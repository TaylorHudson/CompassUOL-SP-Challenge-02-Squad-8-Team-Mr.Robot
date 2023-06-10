package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.utils.Utils;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.CustomerRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.InvalidCpfOrEmailException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CustomerRestController.class)
class CustomerRestControllerTest {

    public static final String BASE_URL = "/v1/customers";
    public static final String ID_URL = "/v1/customers/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void findCustomerByIdSuccess() throws Exception {
        when(customerService.findCustomerById(anyInt())).thenReturn(new CustomerResponseDTO());

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void findCustomerByIdResourceNotFoundException() throws Exception {
        when(customerService.findCustomerById(anyInt())).thenThrow(new ResourceNotFoundException(""));

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createCustomerSuccess() throws Exception {
        var request = new CustomerRequestDTO("john doe","12345678910","john.doe@email.com");
        var responseDTO = new CustomerResponseDTO(1,"john Doe", "12345678910", "john.doe@email.com");

        when(customerService.createCustomer(any(CustomerRequestDTO.class))).thenReturn(responseDTO);

        String requestBody = Utils.mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createCustomerInvalidCpfOrEmailException() throws Exception {
        var request = new CustomerRequestDTO("john doe","12345678910","john.doe@email.com");

        when(customerService.createCustomer(any(CustomerRequestDTO.class))).thenThrow(new InvalidCpfOrEmailException(""));

        String requestBody = Utils.mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void createCustomerMethodArgumentNotValidException() throws Exception {
        var request = new CustomerRequestDTO("j","12345678","john.doe.com");
        var responseDTO = new CustomerResponseDTO();

        when(customerService.createCustomer(any(CustomerRequestDTO.class))).thenReturn(responseDTO);

        String requestBody = Utils.mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void updateCustomerSuccess() throws Exception {
        var request = new CustomerRequestDTO("john doe","12345678910","john.doe@email.com");
        var responseDTO = new CustomerResponseDTO(1,"john Doeing", "12345678910", "john.doe@email.com");

        when(customerService.updateCustomer(anyInt(),any(CustomerRequestDTO.class))).thenReturn(responseDTO);

        String requestBody = Utils.mapToString(request);

        var result =
                mockMvc.perform(put(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateCustomerResourceNotFoundException() throws Exception {
        var request = new CustomerRequestDTO("john doe","12345678910","john.doe@email.com");

        when(customerService.updateCustomer(anyInt(),any(CustomerRequestDTO.class))).thenThrow(new ResourceNotFoundException(""));

        String requestBody = Utils.mapToString(request);

        var result =
                mockMvc.perform(put(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}