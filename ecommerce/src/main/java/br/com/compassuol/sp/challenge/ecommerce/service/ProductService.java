package br.com.compassuol.sp.challenge.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	private final ModelMapper mapper;

	@Autowired
	public ProductService(ProductRepository productRepository, ModelMapper mapper) {
		this.productRepository = productRepository;
		this.mapper = mapper;
	}

	public ProductResponseDTO findProductById(int id) {
		Product customer = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Did not find c with id - " + id));

		return mapper.map(customer, ProductResponseDTO.class);
	}

	public ProductRequestDTO createProduct(ProductRequestDTO product) {

		Product createdProduct = mapper.map(product, Product.class);

		createdProduct = productRepository.save(createdProduct);

		return mapper.map(createdProduct, ProductRequestDTO.class);
	}

	public List<ProductResponseDTO> findAllProducts(){
	    	List<Product> productsList = productRepository.findAll();
	    	
	    	List<ProductResponseDTO> productsListDTO = new ArrayList<>();
	    	
	    	for (Product product : productsList) {
	    		productsListDTO.add(mapper.map(product, ProductResponseDTO.class));
	    	}
	    	
	    	return productsListDTO;
	    }

}
