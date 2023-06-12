package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.PaymentRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.PaymentResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.PaymentMethod;
import br.com.compassuol.sp.challenge.ecommerce.service.PaymentService;
import br.com.compassuol.sp.challenge.ecommerce.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(controllers = PaymentController.class)
public class PaymentRestControllerTest {

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private PaymentController paymentController;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.paymentController);
    }

    @Autowired
    private MockMvc mockMvc;

    private PaymentResponseDTO createPaymentResponse() {
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(1);
        response.setPaymentMethod(PaymentMethod.valueOf("PIX"));
        response.setPaymentDate(LocalDate.ofEpochDay(203 - 06 - 12));
        response.setOrderId(1);
        return response;
    }

    @Test
    void createPaymentSuccess() throws Exception {

        PaymentMethod paymentMethod = PaymentMethod.PIX;
        LocalDate day = LocalDate.of(2023, Month.JANUARY, 25);

        var request = new PaymentRequestDTO(paymentMethod, day, 1);
        var reponseDTO = createPaymentResponse();

        when(paymentService.createPayment(any(PaymentRequestDTO.class))).thenReturn(reponseDTO);

        String requestBody = Utils.mapToString(request);
        var result =
                mockMvc.perform(post("/v1/payments")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
