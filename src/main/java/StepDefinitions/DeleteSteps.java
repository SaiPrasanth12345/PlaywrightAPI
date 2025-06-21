package StepDefinitions;

import DataBody.DataPojo;
import DataBody.Device;
import DataBody.ReqresLombok;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteSteps {
    Playwright playwright;
    APIRequestContext reqContext;
    RequestOptions options;
    APIResponse response;
    APIResponse delResponse;
    Device deviceDetails;
    Device postresponse;
    String generatedID;

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

    @Given("Generate a request body")
    public void generate_a_request_body() {
        //using POJO
        // Data deviceData = new Data(2019, 1849.99, "Intel Core i9", "1 TB");

        //Using Lombok
        DataPojo deviceData = DataPojo.builder()
                .year(2025)
                .price(1783.99)
                .CPU_model("Intel Core i15")
                .size("1 TB")
                .build();

        deviceDetails = Device.builder().name("Samsung Galaxy S25").data(deviceData).build();

        // PUT reponse text
        System.out.println("Request text :" + deviceDetails.toString());
    }
    @Given("perform the POST request {string}")
    public void perform_the_post_request(String url) {
        options = RequestOptions.create()
                .setHeader("application-type", "content/json")
                .setData(deviceDetails);

        response = reqContext.post(url,options);

        // POST reponse text
        System.out.println("Response text :" + response.text());
    }
    @Given("validate POST response status")
    public void validate_post_response_status() {
        System.out.println("-------- POST RESPONSE STATUS ----------");

        //status code & text
        System.out.println("Response status :" + response.status() );

        // status code text
        System.out.println("Response status text:" + response.statusText());
    }

    @Given("validate POST response body")
    public void validate_post_response_body() throws JsonProcessingException {
        String postResponseBody = response.text();

        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        postresponse = objectMapper.readValue(postResponseBody, Device.class);

        //Response details Print
        System.out.println("-------- POST RESPONSE BODY DETAILS----------");
        System.out.println("Device - id:" + postresponse.getId());
        System.out.println("Device - Created At:" + postresponse.getCreatedAt());
        System.out.println("Device - Name:" + postresponse.getName());
        System.out.println("Device - Price:" + postresponse.getData().getPrice());
    }
    @When("Get the id from the POST request")
    public void get_the_id_from_the_post_request() {
        generatedID = postresponse.getId();
        System.out.println("Device - id:" + generatedID);
    }

    @When("Perform DELETE request with URL {string}")
    public void perform_delete_request(String deleteURL) {
        delResponse = reqContext.delete(deleteURL + generatedID);
    }

    @Then("validate the DELETE response status")
    public void validate_the_delete_response_status() {
        System.out.println("-------- DELETE RESPONSE STATUS ----------");

        //status code & text
        System.out.println("Response status :" + delResponse.status() );

        // status code text
        System.out.println("Response status text:" + delResponse.statusText());

        // Response Body
        System.out.println("Delete Response body:" + delResponse.text());

    }

}
