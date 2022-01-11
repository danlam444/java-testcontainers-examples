package com.lam.demo.pizzaservice;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class TestContainerWrapper {
    public static final PostgreSQLContainer PIZZA_DB_CONTAINER;

    public static final GenericContainer<?> ACTIVE_MQ_CONTAINER;

    static {
        PIZZA_DB_CONTAINER =  new PostgreSQLContainer("postgres:11.1");
        PIZZA_DB_CONTAINER.start();

        ACTIVE_MQ_CONTAINER = new GenericContainer<>("vromero/activemq-artemis:latest-alpine")
                .withExposedPorts(61616) //waits until port is available i.e when ActiveMQ is ready
                .withEnv("DISABLE_SECURITY", "true")
                .waitingFor(Wait.forLogMessage(".*AMQ221007: Server is now live.*\n", 1));
        ACTIVE_MQ_CONTAINER.start();
    }
}
