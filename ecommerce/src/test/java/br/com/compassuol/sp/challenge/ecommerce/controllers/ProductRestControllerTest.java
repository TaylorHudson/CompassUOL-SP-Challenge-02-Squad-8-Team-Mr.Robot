package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ProductPriceNotValidException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.service.ProductService;
import br.com.compassuol.sp.challenge.ecommerce.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@WebMvcTest(controllers = ProductRestController.class)
public class ProductRestControllerTest {
    private ProductResponseDTO UpdatedProductResponse() {
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
	@Autowired
    private MockMvc mockMvc;

    public static final String BASE_URL = "/v1/products";
    public static final String ID_URL = "/v1/products/9";

    private ProductResponseDTO createUpdatedProductResponse() {
    	ProductResponseDTO response = new ProductResponseDTO();
    	response.setName("New Product");
    	response.setPrice(9.99);
    	response.setDescription("Updated description");
    	response.setProductId(1);
    	return response;
    }



    @Test
    void findProductByIdSuccess() throws Exception {
        when(productService.findProductById(anyInt())).thenReturn(new ProductResponseDTO());

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void findProductByIdResourceNotFoundException() throws Exception {
        when(productService.findProductById(anyInt())).thenThrow(new ResourceNotFoundException(""));

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void createProductSuccess() throws Exception {
        var request = new ProductRequestDTO("New description", "New Product", 9.99, 1 );
        var responseDTO = createUpdatedProductResponse();

        when(productService.createProduct(any(ProductRequestDTO.class))).thenReturn(responseDTO);

        String requestBody = Utils.mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }


    @Test
    void createProductPriceNotValidException() throws Exception {
        var request = new ProductRequestDTO("New description", "New Product",0, 1);

        when(productService.createProduct(any(ProductRequestDTO.class))).thenThrow(new ProductPriceNotValidException(""));

        String requestBody = Utils.mapToString(request);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void retrieveAllProductsSuccessTest() throws Exception {
        List<ProductResponseDTO> responseDTOS = new ArrayList<>();
        responseDTOS.add(new ProductResponseDTO(1, "New Product", 9.99, "New description"));

        when(productService.findAllProducts()).thenReturn(responseDTOS);

        var result =
                mockMvc.perform(get(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }



    @Test
    void deleteProduct_Success() throws Exception {
        int productId = 1;

        doNothing().when(productService).deleteProduct(productId);

        ProductRestController productController = new ProductRestController(productService);

        mockMvc = standaloneSetup(productController).build();

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
                .thenReturn(UpdatedProductResponse());

        ProductRestController productController = new ProductRestController(productService);
        mockMvc = standaloneSetup(productController).build();


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

        when(productService.updateProduct(productId, productRequest)).thenThrow(new ResourceNotFoundException("Product not found"));

        ProductRestController productController = new ProductRestController(productService);

        mockMvc = standaloneSetup(productController).build();

        mockMvc.perform(put("/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.mapToString(productRequest)))
                .andExpect(status().isOk());
    }
}
