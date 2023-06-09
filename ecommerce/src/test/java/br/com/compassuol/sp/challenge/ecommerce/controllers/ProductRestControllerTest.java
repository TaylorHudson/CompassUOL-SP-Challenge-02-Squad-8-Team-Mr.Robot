package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.Utils;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ProductNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@WebMvcTest
public class ProductRestControllerTest {
    private ProductResponseDTO createUpdatedProductResponse() {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setName("New Product");
        response.setPrice(9.99);
        response.setDescription("Updated description");
        return response;
    }

    @MockBean
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

        mockMvc.perform(delete("/v1/products/{id}", productId))
                .andExpect(status().isOk());
    }



    @Test
    void testUpdateProduct_Success() throws Exception {
        int productId = 1;
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("New Product");
        request.setPrice(9.99);
        request.setDescription("Updated description");

        when(productService.updateProduct(eq(productId), any(ProductRequestDTO.class)))
                .thenReturn(createUpdatedProductResponse());

        ProductRestController productController = new ProductRestController(productService);
        MockMvc mockMvc = standaloneSetup(productController).build();


        ObjectMapper objectMapper = new ObjectMapper();

         mockMvc.perform(put("/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(productService).updateProduct(eq(productId), any(ProductRequestDTO.class));
    }

    @Test
    void testUpdateProduct_ProductNotFoundException() throws Exception {
        int productId = 1;
        ProductRequestDTO productRequest = new ProductRequestDTO();

        when(productService.updateProduct(productId, productRequest)).thenThrow(new ProductNotFoundException("Product not found"));

        ProductRestController productController = new ProductRestController(productService);

        MockMvc mockMvc = standaloneSetup(productController).build();

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.mapToString(productRequest)))
                .andExpect(status().isNotFound());
    }
}
