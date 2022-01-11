Feature: Pizza database

  Scenario: Add pizza via REST
    When I save a pizza with name 'peperoni' with price 1.99
    Then the system has pizza with name 'peperoni' with price 1.99

