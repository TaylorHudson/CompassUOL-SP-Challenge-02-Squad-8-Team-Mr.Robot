package br.com.compassuol.sp.challenge.ecommerce.controllers;

import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import br.com.compassuol.sp.challenge.ecommerce.dto.response.OrderResponseDTO;
import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import br.com.compassuol.sp.challenge.ecommerce.service.OrderService;
import br.com.compassuol.sp.challenge.ecommerce.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = OrderRestController.class)
class OrderRestControllerTest {

    public static final String BASE_URL = "/v1/orders";

    public static final String ID_URL = "/v1/orders/customers/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void findAllOrdersSuccess() throws Exception {
        List<OrderResponseDTO> responseDTOS = new ArrayList<>();
        responseDTOS.add(new OrderResponseDTO(1, 1, LocalDate.now(), Status.CREATED, new ArrayList<>()));

        when(orderService.findAllOrders()).thenReturn(responseDTOS);

        var result =
                mockMvc.perform(get(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createOrderSuccess() throws Exception {
        var request = new OrderRequestDTO(1, new ArrayList<>(), LocalDate.now(), Status.CREATED);
        var responseDTO = new OrderResponseDTO(1, 1, LocalDate.now(), Status.CREATED, new ArrayList<>());

        when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(responseDTO);

        String requestBody = Utils.mapToString(request);

        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void findAllOrderByCustomerId() throws Exception {
        var listResponse = new ArrayList<OrderResponseDTO>();
        listResponse.add(new OrderResponseDTO(1, 1, LocalDate.now(), Status.CREATED, new ArrayList<>()));

        when(orderService.findAllOrderByCustomerId(anyInt())).thenReturn(listResponse);

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
}