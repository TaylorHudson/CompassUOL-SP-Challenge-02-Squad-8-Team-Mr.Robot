package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.PaymentResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.ProductQuantityResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.*;
import br.com.compassuol.sp.challenge.ecommerce.repository.OrderRepository;
import br.com.compassuol.sp.challenge.ecommerce.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final ProductService productService;

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ModelMapper mapper;

    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO){

        var order = orderRepository.findById(paymentRequestDTO.getOrderId());
        var customerResponseDTO = customerService.findCustomerById(order.get().getOrderId());


        var customer = new Customer();
        customer.setCustomerId(customerResponseDTO.getCustomerId());
        customer.setName(customerResponseDTO.getName());
        customer.setCpf(customerResponseDTO.getCpf());
        customer.setEmail(customerResponseDTO.getEmail());
        customer.setActive(true);

       // var order = new Order();
//        order.setOrderId(paymentRequestDTO.getOrderId());
//        order.setCustomer(customer);
//        order.setDate(orderResponseDTO.getDate());
//        order.setProducts(toProductQuantity(orderResponseDTO.getProducts()));
        Payment newPayment  = new Payment();
        newPayment.setPaymentDate(paymentRequestDTO.getPaymentDate());
        newPayment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
        newPayment.setOrder(order.get());

        newPayment = paymentRepository.save(newPayment);

        orderService.updateStatusOrder(order.get().getOrderId(),Status.CONFIRMED);



        return new PaymentResponseDTO(newPayment.getPaymentId(), newPayment.getPaymentMethod(),newPayment.getPaymentDate(),order.get().getOrderId());

    }

    private List<ProductQuantity> toProductQuantity(List<ProductQuantityResponseDTO> response) {
        List<ProductQuantity> products = new ArrayList<>();

        response.forEach(p -> {
            var requestDTO = productService.findProductById(p.getProductId());
            var product = mapper.map(requestDTO, Product.class);

            ProductQuantity productQuantity = new ProductQuantity();
            productQuantity.setProduct(product);
            productQuantity.setQuantity(p.getQuantity());
            products.add(productQuantity);
        });

        return products;
    }
}
