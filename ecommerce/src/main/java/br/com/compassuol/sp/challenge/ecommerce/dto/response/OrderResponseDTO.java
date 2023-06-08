package br.com.compassuol.sp.challenge.ecommerce.dto.response;

import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private int orderId;

    private int customerId;

    private Date date;

    private Status status;

    private List<Product> products;
}
