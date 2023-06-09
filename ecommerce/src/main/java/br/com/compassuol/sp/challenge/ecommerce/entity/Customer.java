package br.com.compassuol.sp.challenge.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Size(min = 3,message = "The name must be at least 3 characters long")
    private String name;

    @Size(min = 11, max = 14,message = "The cpf ins't in the correct size")
    private String cpf;

    @Email(message = "The email must be a well-formed email address")
    private String email;

    private boolean active;

    public Customer(String name, String cpf, String email, boolean active) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.active = active;
    }

}
