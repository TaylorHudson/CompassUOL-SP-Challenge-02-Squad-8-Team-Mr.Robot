package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductQuantityResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.entity.Order;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.entity.ProductQuantity;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.EmptyProductException;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    private final ModelMapper mapper;

    public OrderResponseDTO createOrder(OrderRequestDTO request){

        var customerResponseDTO = customerService.findCustomerById(request.getCustomerId());
        var customer = mapper.map(customerResponseDTO, Customer.class);

        var productsQuantityDTO = request.getProducts();

        if (productsQuantityDTO.isEmpty()){
            throw new EmptyProductException("The Products cannot be empty!");
        }

        List<ProductQuantity> products = new ArrayList<>();
        productsQuantityDTO.forEach(p -> {
            ProductResponseDTO requestDTO = productService.findProductById(p.getProductId());
            Product product = mapper.map(requestDTO, Product.class);

            ProductQuantity productQuantity = new ProductQuantity();
            productQuantity.setProduct(product);
            productQuantity.setQuantity(p.getQuantity());

            products.add(productQuantity);
        });

        List<ProductQuantityResponseDTO> responseDTO = new ArrayList<>();
        productsQuantityDTO.forEach(p -> {
            var productQuantityResponse = new ProductQuantityResponseDTO();
            productQuantityResponse.setProductId(p.getProductId());
            productQuantityResponse.setQuantity(p.getQuantity());

            responseDTO.add(productQuantityResponse);
        });

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setDate(request.getDate());
        order.setStatus(request.getStatus());

        Order saved = orderRepository.save(order);

        return new OrderResponseDTO(saved.getOrderId(), request.getCustomerId(), saved.getDate(), saved.getStatus(),responseDTO);

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
