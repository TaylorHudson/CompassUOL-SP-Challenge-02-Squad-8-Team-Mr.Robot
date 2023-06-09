package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductQuantityRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.*;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.EmptyProductException;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductRepository;
import br.com.compassuol.sp.challenge.ecommerce.utils.OrderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderSuccess() {

        List<ProductQuantityRequestDTO> orderItem = new ArrayList<>();
        orderItem.add(new ProductQuantityRequestDTO());

        Order order = OrderUtil.createOrderDefault();
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1, orderItem,new Date(),Status.CREATED);

        when(customerService.findCustomerById(anyInt())).thenReturn(new CustomerResponseDTO());
        when(productService.findProductById(anyInt())).thenReturn(new ProductResponseDTO());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

       OrderResponseDTO orderResponseDTO =  orderService.createOrder(orderRequestDTO);

       verify(orderRepository).save(any(Order.class));
       assertEquals(order.getOrderId(),orderResponseDTO.getOrderId());

    }

    @Test
    void createOrderEmptyProductException() {

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1, new ArrayList<ProductQuantityRequestDTO>(),new Date(),Status.CREATED);

        when(customerService.findCustomerById(anyInt())).thenReturn(new CustomerResponseDTO());

        assertThrows(EmptyProductException.class , () -> orderService.createOrder(orderRequestDTO));
        verify(orderRepository,times(0)).save(any(Order.class));
    }

    @Test
    void findAllOrdersSuccess() {
        Order order = new Order(1,new Date(), Status.CREATED,new Customer(),new ArrayList<ProductQuantity>());
        List<Order> orderList= new ArrayList<>();
        orderList.add(order);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderResponseDTO> orderResponseDTOList = orderService.findAllOrders();

        verify(orderRepository).findAll();
        assertEquals(1,orderResponseDTOList.size());
        assertEquals(Status.CREATED,orderResponseDTOList.get(0).getStatus());
    }

}