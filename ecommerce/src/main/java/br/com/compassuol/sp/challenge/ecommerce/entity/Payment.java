package br.com.compassuol.sp.challenge.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime paymentDate;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "payment")
    private Order order;
}
