package com.lam.demo.pizzaservice.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PizzaEventMessage implements Serializable {
    private static final long serialVersionUID = -295422703255886286L;
    String name;
    Double price;
}
