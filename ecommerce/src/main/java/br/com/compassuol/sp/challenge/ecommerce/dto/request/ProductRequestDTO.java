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
	
	@Size(min = 5,message = "The description must be at least 3 characters long")
    private String description;

    @Size(min = 3,message = "The name ins't in the correct size")
    private String name;
	
    private double price;

    public int productId;

}
