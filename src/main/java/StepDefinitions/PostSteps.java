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

public class PostSteps {
    Playwright playwright;
    APIRequestContext reqContext;
    APIResponse response;
    Device deviceDetails;
    ReqresLombok reqBody;

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

    @Given("Generate a request body using POJO class")
    public void generate_a_request_body_using_pojo_class() {
        //using POJO
        // Data deviceData = new Data(2019, 1849.99, "Intel Core i9", "1 TB");

        //Using Lombok
        DataPojo deviceData = DataPojo.builder()
                .year(2019)
                .price(1849.99)
                .CPU_model("Intel Core i9")
                .size("1 TB")
                .build();

        deviceDetails = Device.builder().name("Apple MacBook Pro 16").data(deviceData).build();

        // PUT reponse text
        System.out.println("Request text :" + deviceDetails.toString());
    }

    @When("perform POST request {string}")
    public void perform_post_request(String url) {
        RequestOptions options = RequestOptions.create()
                .setHeader("application-type", "content/json")
                .setData(deviceDetails);

        response = reqContext.post(url,options);

        // PUT reponse text
        System.out.println("Response text :" + response.text());

    }

    @Then("validate the POST response status")
    public void validate_the_post_response_status() {
        System.out.println("-------- POST RESPONSE STATUS ----------");

        //status code & text
        System.out.println("Response status :" + response.status() );

        // status code text
        System.out.println("Response status text:" + response.statusText());

    }
    @Then("validate the POST response body")
    public void validate_the_post_response_body() throws JsonProcessingException {
        String postResponseBody = response.text();

        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Device postresponse = objectMapper.readValue(postResponseBody, Device.class);

        //Response details Print
        System.out.println("-------- POST RESPONSE BODY DETAILS----------");
        System.out.println("Device - id:" + postresponse.getId());
        System.out.println("Device - Created At:" + postresponse.getCreatedAt());
        System.out.println("Device - Name:" + postresponse.getName());
        System.out.println("Device - Price:" + postresponse.getData().getPrice());
    }

}
