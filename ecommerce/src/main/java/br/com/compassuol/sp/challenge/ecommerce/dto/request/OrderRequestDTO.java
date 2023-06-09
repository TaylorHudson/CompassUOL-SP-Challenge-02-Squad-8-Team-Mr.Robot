package br.com.compassuol.sp.challenge.ecommerce.dto.request;

import br.com.compassuol.sp.challenge.ecommerce.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

        private int customerId;

//        private List<ProductDTO> products;
        private List<ProductQuantityRequestDTO> products;

        private Date date;

        private Status status;

}
