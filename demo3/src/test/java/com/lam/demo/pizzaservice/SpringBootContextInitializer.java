package com.lam.demo.pizzaservice;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import static com.lam.demo.pizzaservice.TestContainerWrapper.*;

public class SpringBootContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Inject containers' properties into application context
        TestPropertyValues values = TestPropertyValues.of(
                "spring.datasource.url=" + PIZZA_DB_CONTAINER.getJdbcUrl()+"asdad",
                "spring.datasource.username=" + PIZZA_DB_CONTAINER.getUsername(),
                "spring.datasource.password=" + PIZZA_DB_CONTAINER.getPassword(),
                "spring.artemis.port=" + ACTIVE_MQ_CONTAINER.getFirstMappedPort(),
                "spring.artemis.host=" + ACTIVE_MQ_CONTAINER.getHost()
        );

        values.applyTo(applicationContext);
    }
}
