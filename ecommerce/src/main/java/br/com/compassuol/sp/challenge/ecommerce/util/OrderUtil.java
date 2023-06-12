package br.com.compassuol.sp.challenge.ecommerce.util;

import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderUtil {

    private OrderUtil(){}

    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        var dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        dto.setDate(order.getDate());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        dto.setStatus(order.getStatus());
        dto.setProducts(ProductQuantityUtil.entityToResponseDTO(order.getProducts()));
        return dto;
    }

    public static List<OrderResponseDTO> toOrderResponseDTO(List<Order> orders) {
        List<OrderResponseDTO> response = new ArrayList<>();
        orders.forEach(order -> {
            var dto = toOrderResponseDTO(order);
            response.add(dto);
        });
        return response;
    }

}
