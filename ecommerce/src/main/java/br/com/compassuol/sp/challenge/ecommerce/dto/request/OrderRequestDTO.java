package br.com.compassuol.sp.challenge.ecommerce.dto.request;

import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

        private int customerId;

        private List<ProductQuantityRequestDTO> products;

        @Past(message = "The date must be now or in the past")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        private Status status;

}
