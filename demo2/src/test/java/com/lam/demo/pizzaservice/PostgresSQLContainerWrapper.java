package com.lam.demo.pizzaservice;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

@Component
/**
 * Consider concurrent tests, different threads accessing same container.
 * Each thread could use their own schema inside a shared container.
 */
public class PostgresSQLContainerWrapper {

    public static final PostgreSQLContainer PIZZA_DB_CONTAINER;

    static {
        PIZZA_DB_CONTAINER =  new PostgreSQLContainer("postgres:11.1");
        PIZZA_DB_CONTAINER.start();
    }

}
