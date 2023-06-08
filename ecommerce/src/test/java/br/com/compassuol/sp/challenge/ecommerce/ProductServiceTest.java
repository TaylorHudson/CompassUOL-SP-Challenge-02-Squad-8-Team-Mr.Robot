package br.com.compassuol.sp.challenge.ecommerce;


import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
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

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void testDeleteProduct() {

        Product product = new Product();
        product.setProductId(1);
        product.setName("Product Name");
        product.setPrice(9.99);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.deleteProduct(1);

        verify(productRepository).findById(1);

        verify(productRepository).delete(product);
    }
    @Test
    void updateProductErrorResourceNotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findProductById(1));
        verify(productRepository).findById(1);
    }


}

