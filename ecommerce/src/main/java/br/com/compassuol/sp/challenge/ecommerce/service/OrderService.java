package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.entity.Order;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.repository.CustomerRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final ModelMapper mapper;


    public OrderResponseDTO createOrder(OrderRequestDTO order){

        Optional<Customer> customer = customerRepository.findById(order.getCustomerId());

        List<Product> products = new ArrayList<>();

        for (int productId: order.getProducts()) {
           var recievedProduct = productRepository.findById(productId) ;
           products.add(recievedProduct.get());
        }

        Order createdOrder = mapper.map(order,Order.class);

        createdOrder.setProducts(products);
        createdOrder.setCustomer(customer.get());

        createdOrder = orderRepository.save(createdOrder);


        return mapper.map(createdOrder, OrderResponseDTO.class);
    }
    public List<OrderResponseDTO> findAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (Order order: orderList) {
            orderResponseDTOList.add(mapper.map(order, OrderResponseDTO.class));
        }
        return orderResponseDTOList;
    }

}
