package br.com.compassuol.sp.challenge.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import br.com.compassuol.sp.challenge.ecommerce.exceptions.ProductExitsInAOrderException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ProductPriceNotValidException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
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
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Did not find product with id - " + id));

		return mapper.map(product, ProductResponseDTO.class);
	}

	public ProductResponseDTO createProduct(ProductRequestDTO product) {

		Product createdProduct = mapper.map(product, Product.class);

		if(createdProduct.getPrice() <= 0) {
			throw new ProductPriceNotValidException("Product price not valid");
		}

		createdProduct = productRepository.save(createdProduct);

		return mapper.map(createdProduct, ProductResponseDTO.class);
	}

	public List<ProductResponseDTO> findAllProducts(){
	    	List<Product> productsList = productRepository.findAll();
	    	
	    	List<ProductResponseDTO> productsListDTO = new ArrayList<>();
	    	
	    	for (Product product : productsList) {
	    		productsListDTO.add(mapper.map(product, ProductResponseDTO.class));
	    	}
	    	
	    	return productsListDTO;
	    }
	
	
	public ProductResponseDTO updateProduct(int id, ProductRequestDTO request) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The id supplied must be from a product that is already created"));

		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setDescription(request.getDescription());

		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct, ProductResponseDTO.class);
	}
	

	public void deleteProduct(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The product corresponding to this ID does not exist"));

		try {
			productRepository.delete(product);
		}catch (Exception e){
			throw new ProductExitsInAOrderException("The product is related to a order and cant be deleted.")	;
		}
	}



}
