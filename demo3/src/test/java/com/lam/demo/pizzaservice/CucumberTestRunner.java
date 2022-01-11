package com.lam.demo.pizzaservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/reports/cucumber/cucumber.json"},
        features = "classpath:features",
        glue = {"com.lam.demo.pizzaservice"}
)
public class CucumberTestRunner {
}
