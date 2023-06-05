package br.com.compassuol.sp.challenge.ecommerce.repository;

import br.com.compassuol.sp.challenge.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


}
