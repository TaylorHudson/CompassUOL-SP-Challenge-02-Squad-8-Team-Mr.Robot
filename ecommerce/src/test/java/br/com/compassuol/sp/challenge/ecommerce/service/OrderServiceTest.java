package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductQuantityRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.CustomerResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.entity.Order;
import br.com.compassuol.sp.challenge.ecommerce.entity.ProductQuantity;
import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.EmptyProductException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductQuantityRepository;
import br.com.compassuol.sp.challenge.ecommerce.utils.OrderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Mock
    private ProductQuantityRepository productQuantityRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderSuccess() {

        var order = OrderUtil.createOrderDefault();
        var orderRequestDTO = OrderUtil.createOrderRequest();

        var productQuantity = new ProductQuantity();

        when(customerService.findCustomerById(anyInt())).thenReturn(new CustomerResponseDTO());
        when(productService.findProductById(anyInt())).thenReturn(new ProductResponseDTO());
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productQuantityRepository.save(any(ProductQuantity.class))).thenReturn(productQuantity);

       OrderResponseDTO orderResponseDTO =  orderService.createOrder(orderRequestDTO);

       verify(orderRepository).save(any(Order.class));
       assertEquals(order.getOrderId(),orderResponseDTO.getOrderId());

    }

    @Test
    void createOrderEmptyProductException() {

       var orderRequestDTO = new OrderRequestDTO(1, new ArrayList<ProductQuantityRequestDTO>(), LocalDate.now(),Status.CREATED);

        when(customerService.findCustomerById(anyInt())).thenReturn(new CustomerResponseDTO());

        assertThrows(EmptyProductException.class , () -> orderService.createOrder(orderRequestDTO));
        verify(orderRepository,times(0)).save(any(Order.class));
    }

    @Test
    void findAllOrdersSuccess() {
        var order = new Order(1, LocalDate.now(), Status.CREATED,new Customer(),new ArrayList<ProductQuantity>());
        List<Order> orderList= new ArrayList<>();
        orderList.add(order);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderResponseDTO> orderResponseDTOList = orderService.findAllOrders();

        verify(orderRepository).findAll();
        assertEquals(1,orderResponseDTOList.size());
        assertEquals(Status.CREATED,orderResponseDTOList.get(0).getStatus());
    }

    @Test
    void findAllOrderByCustomerId() {
        var customerResponse = new CustomerResponseDTO(1, "John Doe", "12345678910", "john.doe@gmail.com");
        var orders = new ArrayList<Order>();
        orders.add(OrderUtil.createOrderDefault());

        when(customerService.findCustomerById(anyInt())).thenReturn(customerResponse);
        when(orderRepository.findAllByCustomer(any(Customer.class))).thenReturn(orders);

        var ordersResponse = orderService.findAllOrderByCustomerId(anyInt());

        verify(orderRepository).findAllByCustomer(any(Customer.class));
        assertEquals(1, ordersResponse.size());
        assertEquals(1, ordersResponse.get(0).getCustomerId());
    }

    @Test
    void findOrderByIdSuccess(){
        var order = OrderUtil.createOrderDefault();

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));

        var orderResponse = orderService.findOrderById(anyInt());

        assertEquals(order.getOrderId(),orderResponse.getOrderId());
        verify(orderRepository).findById(anyInt());
    }

    @Test
    void findOrderByIdResourceNotFoundException(){

        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> orderService.findOrderById(anyInt()));
        verify(orderRepository).findById(anyInt());
    }


    @Test

    void updateStatusOrderSuccess(){

        var order = OrderUtil.createOrderDefault();

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));

        orderService.updateStatusOrder(anyInt(),Status.CONFIRMED);

        assertEquals(Status.CONFIRMED,order.getStatus());
        verify(orderRepository).findById(anyInt());
    }

    @Test
    void updateStatusOrderResourceNotFoundException(){

        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateStatusOrder(anyInt(),Status.CONFIRMED));
        verify(orderRepository).findById(anyInt());
    }
}