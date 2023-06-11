package br.com.compassuol.sp.challenge.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<ProductQuantity> products;

    public Order(LocalDate date, Status status, Customer customer, List<ProductQuantity> products) {
        this.date = date;
        this.status = status;
        this.customer = customer;
        this.products = products;
    }


    public Order(int orderId, LocalDate date, Status status, Customer customer, List<ProductQuantity> products) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
        this.customer = customer;
        this.products = products;
    }

}
