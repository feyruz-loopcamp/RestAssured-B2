package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HelloWorldApiTest {
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world"; // API Endpoint

    @DisplayName("Hello World GET request")
    @Test
    public void helloWorldGetRequestTest(){
        //send a GET request and save response inside the Response object
        Response response = RestAssured.get(url);

        //Print response body in a formatted way (in this case JSON format) - RESPONSE BODY
        response.prettyPrint(); // this also returns String.


        //Print status code - RESPONSE CODE
        System.out.println("Status Code: " + response.statusCode());  // 200
        System.out.println("Status Line: " + response.statusLine());  // HTTP/1.1 200 OK

        // assert/validate that Response Code is 200
        Assertions.assertEquals(200, response.statusCode(), "Did not match");

        // You can also assign RESPONSE STATUS CODE into a variable and then use variable to assert
        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode, "Did not match");

        // .asString(); method will return the RESPONSE BODY as a String.
        String actualResponseBody = response.asString();
        //System.out.println(actualResponseBody);

        // Can you assert the Hello World! is in the body?
        Assertions.assertTrue( actualResponseBody.contains("Hello World!")  );

    }

}