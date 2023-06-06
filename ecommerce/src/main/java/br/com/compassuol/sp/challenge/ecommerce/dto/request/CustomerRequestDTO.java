package br.com.compassuol.sp.challenge.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDTO {

    @Size(min = 3)
    private String name;

    @Size(min = 11, max = 14)
    private String cpf;

    @Email
    private String email;

}
