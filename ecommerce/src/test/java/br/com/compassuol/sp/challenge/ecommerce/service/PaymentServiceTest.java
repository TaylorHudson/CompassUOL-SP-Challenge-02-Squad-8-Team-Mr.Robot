package br.com.compassuol.sp.challenge.ecommerce.service;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Payment;
import br.com.compassuol.sp.challenge.ecommerce.entity.PaymentMethod;
import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import br.com.compassuol.sp.challenge.ecommerce.repository.PaymentRepository;
import br.com.compassuol.sp.challenge.ecommerce.utils.OrderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderService orderService;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private PaymentService service;

    @Test
    void createPayment() {
        var paymentRequestDTO = new PaymentRequestDTO(PaymentMethod.CREDIT_CARD, LocalDate.now(),1);
        var order = OrderUtil.createOrderDefault();
        var newPayment = new Payment(1,paymentRequestDTO.getPaymentMethod(),paymentRequestDTO.getPaymentDate(),order);

        when(orderService.findOrderById(anyInt())).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(newPayment);

        var response = service.createPayment(paymentRequestDTO);

        assertEquals(paymentRequestDTO.getPaymentMethod(), response.getPaymentMethod());
        assertEquals(paymentRequestDTO.getPaymentDate(), response.getPaymentDate());
        assertEquals(order.getOrderId(), response.getOrderId());
        verify(orderService).findOrderById(response.getOrderId());
        verify(paymentRepository).save(any(Payment.class));
        verify(orderService).updateStatusOrder(response.getOrderId(), Status.CONFIRMED);
    }

}