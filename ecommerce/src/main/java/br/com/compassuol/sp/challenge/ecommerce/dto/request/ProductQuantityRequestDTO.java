package br.com.compassuol.sp.challenge.ecommerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityRequestDTO {

    private int productId;

    private int quantity;

}
