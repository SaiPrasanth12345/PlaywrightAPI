Feature: POST Request

  @POST
  Scenario: Perform POST Request using Playwright API
    Given Generate a request body using POJO class
    When perform POST request "https://api.restful-api.dev/objects"
    Then validate the POST response status
    And validate the POST response body