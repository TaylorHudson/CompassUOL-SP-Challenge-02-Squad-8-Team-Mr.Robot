package br.com.compassuol.sp.challenge.ecommerce.service;

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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    private Product createProductDefault() {
        Product product = new Product("Skirt", 54.99, "Fabric cotton" );
        product.setProductId(1);

        return product;
    }

    private ProductResponseDTO createExpectedResponseDefault() {
        return new ProductResponseDTO(1, "Skirt", 54.99, "Fabric cotton");
    }

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

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



