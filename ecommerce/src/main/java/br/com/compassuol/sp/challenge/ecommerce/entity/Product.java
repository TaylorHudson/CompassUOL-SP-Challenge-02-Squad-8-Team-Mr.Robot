package br.com.compassuol.sp.challenge.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id")
    private int productId;

    @Size(min = 2,message = "The name must be at least 2 characters long")
    private String name;

    private double price ;

    @Size(min = 3,message = "The description must be at least 3 characters long")
    private String description;

	public Product( String name, double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
	}

    
}
