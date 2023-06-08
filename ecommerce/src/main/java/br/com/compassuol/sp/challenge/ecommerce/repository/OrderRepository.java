package br.com.compassuol.sp.challenge.ecommerce.repository;

import br.com.compassuol.sp.challenge.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
