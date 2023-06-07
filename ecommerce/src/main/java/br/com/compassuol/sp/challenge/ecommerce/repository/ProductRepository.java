package br.com.compassuol.sp.challenge.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compassuol.sp.challenge.ecommerce.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {

}
