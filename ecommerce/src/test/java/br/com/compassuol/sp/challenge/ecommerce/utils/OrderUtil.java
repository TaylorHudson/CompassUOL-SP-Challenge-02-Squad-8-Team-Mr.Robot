package br.com.compassuol.sp.challenge.ecommerce.utils;

import br.com.compassuol.sp.challenge.ecommerce.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderUtil {

    public static Order createOrderDefault(){
        Customer customer = new Customer("John Doe", "12345678910", "john.doe@gmail.com", true);
        Product product = new Product("Carrinho",1000,"nice");
        product.setProductId(1);
        ProductQuantity productQuantity = new ProductQuantity(1,product,new Order(),5);
        List<ProductQuantity> orderProductList = new ArrayList<>();
        orderProductList.add(productQuantity);
        return new Order(1,new Date(), Status.CREATED,customer,orderProductList);


    }
}
