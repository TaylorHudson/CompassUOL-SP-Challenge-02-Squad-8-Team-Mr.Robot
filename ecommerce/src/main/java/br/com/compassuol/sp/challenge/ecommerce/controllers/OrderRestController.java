package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> findAllOrders(){
        List<OrderResponseDTO> orderResponseDTOList = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOList);
    }

    @PostMapping("/orders")

    public ResponseEntity<OrderResponseDTO> createOrder(OrderRequestDTO order){
        var createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(createdOrder);
    }
}
