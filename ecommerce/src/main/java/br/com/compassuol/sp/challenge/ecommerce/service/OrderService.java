package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.ProductQuantityRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductQuantityResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.*;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.EmptyProductException;
import br.com.compassuol.sp.challenge.ecommerce.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.ProductQuantityRepository;
import br.com.compassuol.sp.challenge.ecommerce.util.OrderUtil;
import br.com.compassuol.sp.challenge.ecommerce.util.ProductQuantityUtil;
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

    private final ProductQuantityRepository productQuantityRepository;

    private final ProductService productService;

    private final ModelMapper mapper;

    public OrderResponseDTO createOrder(OrderRequestDTO request){

        var customerResponseDTO = customerService.findCustomerById(request.getCustomerId());
        var customer = mapper.map(customerResponseDTO, Customer.class);

        var listRequestDTO = request.getProducts();

        if (listRequestDTO.isEmpty()) {
            throw new EmptyProductException("The Products cannot be empty!");
        }

        List<ProductQuantity> products = toProductQuantity(listRequestDTO);
        List<ProductQuantityResponseDTO> responseDTO = ProductQuantityUtil.requestToResponseDTO(listRequestDTO);

        Order order = new Order(request.getDate(), request.getStatus(), customer, products);
        Order saved = orderRepository.save(order);

        products.forEach(productQuantity -> {
            productQuantity.setOrder(saved);
            productQuantityRepository.save(productQuantity);
        });

        return new OrderResponseDTO(saved.getOrderId(), request.getCustomerId(), saved.getDate(), saved.getStatus(),responseDTO);

    }

    public List<OrderResponseDTO> findAllOrderByCustomerId(Integer customerId) {
        var customerResponse = customerService.findCustomerById(customerId);
        var customer = mapper.map(customerResponse, Customer.class);

        List<Order> allByCustomer = orderRepository.findAllByCustomer(customer);
        return OrderUtil.toOrderResponseDTO(allByCustomer);
    }

    public List<OrderResponseDTO> findAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return OrderUtil.toOrderResponseDTO(orderList);
    }

    public Order findOrderById(int id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find order with id - " + id));
    }

    public Order updateStatusOrder(int id, Status status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The id supplied must be from a order that is already created"));
        order.setStatus(status);
       return orderRepository.save(order);
    }

    private List<ProductQuantity> toProductQuantity(List<ProductQuantityRequestDTO> request) {
        List<ProductQuantity> products = new ArrayList<>();

        request.forEach(p -> {
            ProductResponseDTO requestDTO = productService.findProductById(p.getProductId());
            Product product = mapper.map(requestDTO, Product.class);

            ProductQuantity productQuantity = new ProductQuantity();
            productQuantity.setProduct(product);
            productQuantity.setQuantity(p.getQuantity());

            products.add(productQuantity);
        });

        return products;
    }

}
