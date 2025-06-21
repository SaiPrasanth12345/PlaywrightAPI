Feature: DELETE Request

  #FIRST CREATE A NEW REQUEST, THEN DELETE THE REQUEST
  @DELETE
  Scenario: Perform POST Request using Playwright API
    Given Generate a request body
    And perform the POST request "https://api.restful-api.dev/objects"
    And validate POST response status
    And validate POST response body
    When Get the id from the POST request
    And Perform DELETE request with URL "https://api.restful-api.dev/objects/"
    Then validate the DELETE response status
