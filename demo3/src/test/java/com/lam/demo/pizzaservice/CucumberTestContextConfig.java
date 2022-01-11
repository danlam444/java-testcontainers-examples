package com.lam.demo.pizzaservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
/*
* configuration class which scans for all test components in the “bdd” package and registers them in the context.
* The test is not running without this configuration because Cucumber test steps rely on action components to perform test activities.
 * */
@TestConfiguration
@ComponentScan(basePackages = {"com.lam.demo.pizzaservice"})
public class CucumberTestContextConfig {
}
