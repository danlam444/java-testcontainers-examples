package com.lam.demo.pizzaservice.repository;

import com.lam.demo.pizzaservice.entity.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Integer> {
    Pizza findByName(String name);
}
