package br.com.compassuol.sp.challenge.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "order_id")
    private Order order;
}
