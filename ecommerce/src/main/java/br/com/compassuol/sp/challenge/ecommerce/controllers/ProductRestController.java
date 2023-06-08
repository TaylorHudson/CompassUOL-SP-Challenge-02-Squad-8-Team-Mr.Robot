package br.com.compassuol.sp.challenge.ecommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
public class ProductRestController {
		
	 private final ProductService productService;

	    public ProductRestController(ProductService productService) {
	        this.productService = productService;
	    }

	    @GetMapping("/products/{id}")
	    public ResponseEntity<ProductRequestDTO> findProductById(@PathVariable int id){
	        var productDTO = productService.findProductById(id);
	        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
	    }
	    
	    @GetMapping("/products")
	    public ResponseEntity<List<ProductResponseDTO>> retrieveAllProducts(){
	    	var retrieveAll = productService.findAllProducts();
	    	return ResponseEntity.status(HttpStatus.OK).body(retrieveAll);
	    }

	    @PostMapping("/products")
	    public ResponseEntity<ProductRequestDTO> createProduct(@Valid @RequestBody ProductRequestDTO product) {
	        var createdProduct = productService.createProduct(product);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	    }

		@DeleteMapping("/products/{id}")
		public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable int id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok().build();
		}

		@PutMapping("/products/{id}")
		public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDTO product) {
			var updatedProduct = productService.updateProduct(id, product);
			return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
		}
}
