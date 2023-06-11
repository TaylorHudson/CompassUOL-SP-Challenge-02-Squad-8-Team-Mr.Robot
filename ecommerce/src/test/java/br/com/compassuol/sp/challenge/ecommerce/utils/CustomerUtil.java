package br.com.compassuol.sp.challenge.ecommerce.utils;

import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;

public class CustomerUtil {

    public static Customer createCustomerDefault() {
        Customer customer = new Customer("John Doe", "12345678910", "john.doe@gmail.com", true);
        customer.setCustomerId(1);
        return customer;
    }

    public static CustomerResponseDTO createExpectedResponseDefault() {
        return new CustomerResponseDTO(1,"John Doe", "12345678910", "john.doe@gmail.com");
    }
}
