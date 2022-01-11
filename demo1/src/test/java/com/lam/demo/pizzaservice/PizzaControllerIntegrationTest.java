package com.lam.demo.pizzaservice;


import com.lam.demo.pizzaservice.messaging.PizzaEventMessage;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

/*
* JUNIT5 / Jupiter annotations for testcontainers
* junit4 will use @ClassRule instead  of @Container
* */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PizzaServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers //starts and stops containers before and after test
public class PizzaControllerIntegrationTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ServletWebServerApplicationContext server;

    @LocalServerPort
    private int port;

    private String baseUrl;

    //making container final to share between methods, otherwise new one every test
    @Container
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1");

    @Container //mark container to be managed by TestContainers
    private static final GenericContainer<?> activeMQContainer = new GenericContainer<>("vromero/activemq-artemis:latest-alpine")
            .withExposedPorts(61616) //waits until port is available i.e when ActiveMQ is ready
            .withEnv("DISABLE_SECURITY", "true")
            .waitingFor(Wait.forLogMessage(".*AMQ221007: Server is now live.*\n", 1));;


    @DynamicPropertySource //point app under test to test containers
    static void containerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.artemis.port", activeMQContainer::getFirstMappedPort);
        registry.add("spring.artemis.host", activeMQContainer::getHost);
    }

    @BeforeEach
    public void setup(){
        baseUrl = String.format("http://localhost:%d", port);
    }

    @Test
    public void savePizzaByREST(){
        String pizzaJson =
                "{\n" +
                "\"name\": \"Hawaiian\",\n" +
                "\"price\" : 4.99\n" +
                "}";

        //save pizza
        given().body(pizzaJson)
                .contentType(ContentType.JSON)
                .when().post(baseUrl + "/pizza")
                .then().statusCode(201);

        //retrieve saved pizza
        System.out.println("savePizzaByREST GET");
        given().param("name", "Hawaiian")
                .when().get(baseUrl + "/pizza").prettyPeek()
                .then().statusCode(200);
    }

    @Test
    public void savePizzaByJmsMessage(){
        //create pizza message
        PizzaEventMessage pizza = PizzaEventMessage.builder().name("Pepperoni").price(5.99).build();

        //send pizza event to save it
        jmsTemplate.convertAndSend("pizza-event-queue", pizza);

        //retrieve saved pizza
        System.out.println("savePizzaByJmsMessage GET");
        given().param("name", "Pepperoni")
                .when().get(baseUrl + "/pizza").prettyPeek()
                .then().statusCode(200);
    }



}
