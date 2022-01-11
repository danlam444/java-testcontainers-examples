package com.lam.demo.pizzaservice;

import com.lam.demo.pizzaservice.entity.Pizza;
import com.lam.demo.pizzaservice.repository.PizzaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PizzaController {

    PizzaRepository pizzaRepository;

    @PostMapping("/pizza")
    public ResponseEntity addPizza(@RequestBody Pizza pizza){
        Pizza savedPizza = pizzaRepository.save(pizza);
        return new ResponseEntity(savedPizza, HttpStatus.CREATED);
    }

    @GetMapping("/pizza")
    public Pizza getPizza(@RequestParam String name){
        return pizzaRepository.findByName(name);
    }
}
