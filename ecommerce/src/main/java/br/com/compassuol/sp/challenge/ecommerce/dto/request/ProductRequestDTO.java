package br.com.compassuol.sp.challenge.ecommerce.dto.request;


import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private String description;

    private String name;

    private double price;

    public int productId;

}
