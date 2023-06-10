package br.com.compassuol.sp.challenge.ecommerce.dto.request;

import br.com.compassuol.sp.challenge.ecommerce.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    private PaymentMethod paymentMethod;

    private LocalDateTime paymentDate;

    private int orderId;

}
