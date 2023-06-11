package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO order){
        var createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/orders/customers/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> findAllOrderByCustomerId(@PathVariable Integer customerId){
        var orders = orderService.findAllOrderByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
