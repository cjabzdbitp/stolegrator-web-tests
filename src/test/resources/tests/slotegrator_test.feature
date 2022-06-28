Feature: Is Slotegrator admin panel works well
  We need working admin dashboard

  Scenario: I can login in admin panel
    Given I go to login page
    When I enter credentials and submit form
    Then I see admin dashboard

  Scenario: Players list is loading
    Given I'm logged in
    When I go to players page
    Then  I see a list of players

  Scenario: Players table sorting works
    Given I'm logged on players page
    When I click sort by registration
    Then  Users sorted right