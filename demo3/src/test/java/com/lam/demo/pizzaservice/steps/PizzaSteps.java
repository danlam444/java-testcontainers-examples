package com.lam.demo.pizzaservice.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PizzaSteps {

    @LocalServerPort
    private int port;

    @Given("I save a pizza with name 'peperoni' with price 1.99")
    public void savePizzaViaRest() throws IOException {
        String baseUrl = String.format("http://localhost:%d", port);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl + "/pizza");

        String pizzaJson =
                "{\n" +
                        "\"name\": \"Hawaiian\",\n" +
                        "\"price\" : 4.99\n" +
                        "}";
        StringEntity entity = new StringEntity(pizzaJson);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(201));
        client.close();
    }

    @Then("the system has pizza with name 'peperoni' with price 1.99")
    public void getPizzaViaRest() throws IOException, InterruptedException, URISyntaxException {
        String baseUrl = String.format("http://localhost:%d/pizza", port);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(baseUrl);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("name", "Hawaiian")
                .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        CloseableHttpResponse response = client.execute(httpGet);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        String responseString = new BasicResponseHandler().handleResponse(response);
        System.out.println("Response body: " + responseString);

        client.close();
    }
}
