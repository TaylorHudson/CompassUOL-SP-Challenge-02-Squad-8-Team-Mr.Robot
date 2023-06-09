package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.Utils;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest
public class ProductRestControllerTest {

    @InjectMocks
    private ProductService productService;

    @Autowired
    private ProductRestController productRestController;

    @BeforeEach
    public void setup(){
        standaloneSetup(this.productRestController);
    }

    @Test
    void deleteProduct_Success() throws Exception {
        int productId = 1;

        doNothing().when(productService).deleteProduct(productId);

        ProductRestController productController = new ProductRestController(productService);

        MockMvc mockMvc = standaloneSetup(productController).build();

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct_Success() throws Exception {
        int productId = 1;
        ProductRequestDTO productRequest = new ProductRequestDTO();
        ProductResponseDTO productResponse = new ProductResponseDTO();

        when(productService.updateProduct(productId, productRequest)).thenReturn(productResponse);

        ProductRestController productController = new ProductRestController(productService);

        MockMvc mockMvc = standaloneSetup(productController).build();

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.mapToString(productRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct_ResourceNotFoundException() throws Exception {
        int productId = 1;
        ProductRequestDTO productRequest = new ProductRequestDTO();

        when(productService.updateProduct(productId, productRequest)).thenThrow(new ResourceNotFoundException(""));

        ProductRestController productController = new ProductRestController(productService);

        MockMvc mockMvc = standaloneSetup(productController).build();

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.mapToString(productRequest)))
                .andExpect(status().isOk());
    }
}
