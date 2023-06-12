package br.com.compassuol.sp.challenge.ecommerce.util;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductQuantityRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductQuantityResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.ProductQuantity;

import java.util.ArrayList;
import java.util.List;

public class ProductQuantityUtil {

    private ProductQuantityUtil(){}

    public static ProductQuantityResponseDTO entityToResponseDTO(ProductQuantity productQuantity) {
        var product = new ProductQuantityResponseDTO();
        product.setProductId(productQuantity.getProduct().getProductId());
        product.setQuantity(productQuantity.getQuantity());
        return product;
    }

    public static ProductQuantityResponseDTO requestToResponseDTO(ProductQuantityRequestDTO request) {
        var productQuantityResponse = new ProductQuantityResponseDTO();
        productQuantityResponse.setProductId(request.getProductId());
        productQuantityResponse.setQuantity(request.getQuantity());
        return productQuantityResponse;
    }

    public static List<ProductQuantityResponseDTO> requestToResponseDTO(List<ProductQuantityRequestDTO> request) {
        List<ProductQuantityResponseDTO> responseDTO = new ArrayList<>();
        request.forEach(requestDTO -> {
            var product = requestToResponseDTO(requestDTO);
            responseDTO.add(product);
        });
        return responseDTO;
    }

    public static List<ProductQuantityResponseDTO> entityToResponseDTO(List<ProductQuantity> productQuantities) {
        List<ProductQuantityResponseDTO> responseDTO = new ArrayList<>();
        productQuantities.forEach(productQ -> {
            var product = entityToResponseDTO(productQ);
            responseDTO.add(product);
        });
        return responseDTO;
    }

}
