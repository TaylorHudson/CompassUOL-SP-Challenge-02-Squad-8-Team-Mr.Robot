package br.com.compassuol.sp.challenge.ecommerce.utils;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductQuantityRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderUtil {

    public static Order createOrderDefault(){
        Customer customer = new Customer("John Doe", "12345678910", "john.doe@gmail.com", true);
        customer.setCustomerId(1);
        Product product = new Product("Carrinho",1000,"nice");
        product.setProductId(1);
        ProductQuantity productQuantity = new ProductQuantity(1,product,new Order(),5);
        List<ProductQuantity> orderProductList = new ArrayList<>();
        orderProductList.add(productQuantity);
        return new Order(1, LocalDate.now(), Status.CREATED,customer,orderProductList);


    }

    public static OrderRequestDTO createOrderRequest(){
        List<ProductQuantityRequestDTO> orderItem = new ArrayList<>();
        orderItem.add(new ProductQuantityRequestDTO());

        return new OrderRequestDTO(1, orderItem, LocalDate.now(),Status.CREATED);
    }
}
