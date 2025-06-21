package StepDefinitions;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class StepDefs {
    Playwright playwright;
    APIRequestContext reqContext;
    APIResponse response;

    @Before
    public void playwrightInitiate(Scenario s) {
        s.log("Starting Plawright Instance");

        playwright = Playwright.create();
        APIRequest request = playwright.request();
        reqContext = request.newContext();
    }

    @After
    public void closeplayWright(Scenario s){
        s.log("Closing the Plawright Instance");
        playwright.close();
    }


    @When("perform GET request {string}")
    public void perform_get_request(String baseurl) {
        response = reqContext.get(baseurl,
                RequestOptions.create()
                        .setHeader("application-type", "content/json")
                        // Full uRL - https://api.restful-api.dev/objects?id=3
                        .setQueryParam("id",3));
    }

    @Then("validate the response status")
    public void validate_the_response_status() {
        //status code
        System.out.println("Response status :" + response.status());

        // status code text
        System.out.println("Response status text:" + response.statusText());
    }

    @Then("validate the response body")
    public void validate_the_response_body() throws IOException {
        // Get reponse text
        System.out.println("Response text :" + response.text());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node =  mapper.readTree(response.body());

        // node.get(0) - > to get the details of first elements in the array
        // Get Mobile Name
        System.out.println("Mobile Name :" + node.get(0).get("name"));

        // Get Mobile Color
        System.out.println("Mobile Color :" + node.get(0).get("data").get("color"));

        // Get Mobile Capacity
        System.out.println("Mobile Capacity :" + node.get(0).get("data").get("capacity GB"));

    }

}
