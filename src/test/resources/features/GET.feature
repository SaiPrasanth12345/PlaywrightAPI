Feature: GET Request

  @GET
  Scenario: Perform GET Request using Playwright API
    When perform GET request "https://api.restful-api.dev/objects"
    Then validate the response status
    And validate the response body