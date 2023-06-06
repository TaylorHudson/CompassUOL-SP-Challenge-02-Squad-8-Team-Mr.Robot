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

    @Size(min = 3,message = "The name must be at least 3 characters long")
    private String name;

    @Size(min = 11, max = 14,message = "The cpf ins't in the correct size")
    private String cpf;

    @Email(message = "The email must be a well-formed email address")
    private String email;

}
