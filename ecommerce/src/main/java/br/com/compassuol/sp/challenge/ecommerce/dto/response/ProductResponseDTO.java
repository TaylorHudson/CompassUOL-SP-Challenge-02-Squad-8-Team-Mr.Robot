package br.com.compassuol.sp.challenge.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
	@JsonIgnore
	private int productId;

	private String name;
	
	private double price;
	
	private String description;
}
