package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductQuantityRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductQuantityResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import br.com.compassuol.sp.challenge.ecommerce.entity.Order;
import br.com.compassuol.sp.challenge.ecommerce.entity.Product;
import br.com.compassuol.sp.challenge.ecommerce.entity.ProductQuantity;
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

    private final CustomerService customerService;

    private final ProductService productService;

    private final ModelMapper mapper;


//    public OrderResponseDTO createOrder(OrderRequestDTO request){
//
//        var responseDTO = customerService.findCustomerById(request.getCustomerId());
//        var customer = mapper.map(responseDTO, Customer.class);
//
//        List<ProductDTO> productsDTO = request.getProducts();
//        List<Product> products = new ArrayList<>();
//        productsDTO.forEach(p -> {
//            ProductRequestDTO requestDTO = productService.findProductById(p.getProductId());
//            Product product = mapper.map(requestDTO, Product.class);
//            products.add(product);
//        });
//
//        Order order = new Order();
//        order.setCustomer(customer);
//        order.setProducts(products);
//        order.setDate(request.getDate());
//        order.setStatus(request.getStatus());
//
//        Order saved = orderRepository.save(order);
//
//        return new OrderResponseDTO(saved.getOrderId(), request.getCustomerId(), saved.getDate(), saved.getStatus(), productsDTO);
//
//    }

    public OrderResponseDTO createOrder(OrderRequestDTO request){

        var customerResponseDTO = customerService.findCustomerById(request.getCustomerId());
        var customer = mapper.map(customerResponseDTO, Customer.class);

        var productsQuantityDTO = request.getProducts();

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
