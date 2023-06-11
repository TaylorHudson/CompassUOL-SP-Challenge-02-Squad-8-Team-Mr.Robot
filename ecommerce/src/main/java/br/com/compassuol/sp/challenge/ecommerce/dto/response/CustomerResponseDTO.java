package br.com.compassuol.sp.challenge.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

    private int customerId;

    private String name;

    private String cpf;

    private String email;

}
