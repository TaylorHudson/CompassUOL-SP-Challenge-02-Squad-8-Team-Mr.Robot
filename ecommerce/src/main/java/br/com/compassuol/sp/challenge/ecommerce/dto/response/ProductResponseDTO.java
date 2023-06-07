package br.com.compassuol.sp.challenge.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

	private int productId;
	
	private String name;
	
	private double price;
	
	private String description;
}
