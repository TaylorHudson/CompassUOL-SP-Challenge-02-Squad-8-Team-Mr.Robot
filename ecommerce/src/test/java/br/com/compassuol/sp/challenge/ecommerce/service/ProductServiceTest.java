package br.com.compassuol.sp.challenge.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ProductNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ProductPriceNotValidException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductRepository;
import br.com.compassuol.sp.challenge.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product createProductDefault() {
        Product product = new Product("Skirt", 54.99, "Fabric cotton" );
        product.setProductId(1);

        return product;
    }

    private ProductResponseDTO createExpectedResponseDefault() {
        return new ProductResponseDTO(1, "Skirt", 54.99, "Fabric cotton");
    }
    
    
	@Test
    public void findProductByIdSuccessTest() {

        Product product = createProductDefault();
        ProductResponseDTO expectedResponse = createExpectedResponseDefault();
    	
    	when(productRepository.findById(any())).thenReturn(Optional.of(product));
    	
    	ProductResponseDTO request = productService.findProductById(1);
    	
    	assertAll("response",
    			() -> assertEquals(expectedResponse.getName(), request.getName()),
    			() -> assertEquals(expectedResponse.getPrice(), request.getPrice(), 0.01),
    			() -> assertEquals(expectedResponse.getDescription(), request.getDescription()),
    			() -> assertEquals(1, request.getProductId())
    		);
    	verify(productRepository).findById(1);
    }

	
    @Test
    public void findProductByIdErrorResourceNotFoundTest() throws ResourceNotFoundException{
    	
    	
    	when(productRepository.findById(1)).thenThrow(ResourceNotFoundException.class);
    	
        assertThrows(ResourceNotFoundException.class, () -> productService.findProductById(1));
        verify(productRepository).findById(1);
    }

    
    
    @Test
    public void createProductSuccessTest(){
    	
        ProductRequestDTO productRequest = new ProductRequestDTO("Fabric cotton", "Skirt", 54.99, 1);
        Product product = createProductDefault();
                
       
        	when(productRepository.save(any(Product.class))).thenReturn(product);
        
        	ProductResponseDTO expectedResponse = createExpectedResponseDefault();
            ProductResponseDTO response = productService.createProduct(productRequest);
        	
        	assertAll("response",
        		() -> assertEquals(expectedResponse.getName(), response.getName()),
        		() -> assertEquals(expectedResponse.getPrice(), response.getPrice(), 0.01),
        		() -> assertEquals(expectedResponse.getDescription(), response.getDescription()),
        		() -> assertEquals(1, response.getProductId())
        );
        	
        verify(productRepository).save(any(Product.class));  
    }
    
    @Test
    public void findAllProductsSuccessTest() {
    	
    	Product product1 = new Product("Skirt", 54.99, "Fabric cotton" );
        product1.setProductId(1);
    	Product product2 = new Product("Pants", 89.99, "Jeans" );
        product2.setProductId(2);
        
    	List<Product> products = new ArrayList<>();
    	
    	products.add(product1);
    	products.add(product2);
    	
    	ProductResponseDTO product3 = new ProductResponseDTO(1, "Skirt", 54.99, "Fabric cotton" );
    	ProductResponseDTO product4 = new ProductResponseDTO(2,"Pants", 89.99,"Jeans" );
    	
        List<ProductResponseDTO> expectedProducts = new ArrayList<>();
        expectedProducts.add(product3);
    	expectedProducts.add(product4);
    	
    	when(productRepository.findAll()).thenReturn(products);
    	
    	List<ProductResponseDTO> requestProducts = productService.findAllProducts();
    	
    	int i =0;
    
    	while(i<expectedProducts.size()) {
    		for (ProductResponseDTO expectedResponseDTO : expectedProducts) {
    			 assertEquals(expectedResponseDTO.getName(), requestProducts.get(i).getName());
    			 assertEquals(expectedResponseDTO.getPrice(), requestProducts.get(i).getPrice(), 0.01);
    			 assertEquals(expectedResponseDTO.getDescription(), requestProducts.get(i).getDescription());
    			 assertEquals(expectedResponseDTO.getProductId(), requestProducts.get(i).getProductId());
    			 
    			 i++;
    		}
		}
    }
    
    @Test
    void testDeleteProduct() {
        Product product = createProductDefault();

        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        productService.deleteProduct(product.getProductId());

        verify(productRepository).findById(product.getProductId());
        verify(productRepository).delete(product);

    }


    @Test
    void testUpdateProduct() {

        Product product = createProductDefault();
        product = productRepository.save(product);
        
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("New Skirt");
        request.setPrice(59.99);
        request.setDescription("Fabric silk");

        ProductResponseDTO response = productService.updateProduct(product.getProductId(), request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getPrice(), response.getPrice(), 0.001);
        assertEquals(request.getDescription(), response.getDescription());
    }

    @Test
    void updateProductErrorResourceNotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(1));
        verify(productRepository).findById(1);
    }

}



