package br.com.compassuol.sp.challenge.ecommerce;

import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductRepository;
import br.com.compassuol.sp.challenge.ecommerce.service.ProductService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;
    @Test
    public void testDeleteProduct() {

        Product product = new Product();
        product.setProductId(1);
        product.setName("Product Name");
        product.setPrice(9.99);


        when(productRepository.findById(1)).thenReturn(Optional.of(product));


        productService.deleteProduct(1);


        verify(productRepository).findById(1);

        verify(productRepository).delete(product);
    }
}
