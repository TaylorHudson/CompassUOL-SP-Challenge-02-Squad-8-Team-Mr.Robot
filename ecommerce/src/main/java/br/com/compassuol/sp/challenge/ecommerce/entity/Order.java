package br.com.compassuol.sp.challenge.ecommerce.entity;
import br.com.compassuol.sp.challenge.ecommerce.dto.request.OrderRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Past(message = "The date must be now or in the past")
    private Date date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductQuantity> products;


    @OneToOne(fetch = FetchType.LAZY,mappedBy = "order",cascade = CascadeType.ALL)
    private Payment payment;
}
