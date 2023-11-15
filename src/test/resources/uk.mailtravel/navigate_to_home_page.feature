Feature: Navigate to home page

  Scenario: Navigate
    Given I am on the home page
    When I click Accept All Cookies
    Then The page title should have "Home Page | Mail Travel"