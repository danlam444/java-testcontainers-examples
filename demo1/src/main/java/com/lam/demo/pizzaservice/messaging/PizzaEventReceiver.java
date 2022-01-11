package com.lam.demo.pizzaservice.messaging;

import com.lam.demo.pizzaservice.entity.Pizza;
import com.lam.demo.pizzaservice.repository.PizzaRepository;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PizzaEventReceiver {

    PizzaRepository pizzaRepository;

    @JmsListener(destination = "${pizza-event-queue}")
    public void receivePizzaEvent(PizzaEventMessage pizzaEventMessage){
        pizzaRepository.save(Pizza.builder()
                .name(pizzaEventMessage.name)
                .price(pizzaEventMessage.price)
                .build());
    }

}
