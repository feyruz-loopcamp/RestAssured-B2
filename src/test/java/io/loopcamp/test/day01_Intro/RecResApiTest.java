package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class RecResApiTest {
    /**
     * When user sends GET request to
     *      https://reqres.in/api/users/
     *-------------------------------------------------
     * Then RESPONSE STATUS CODE is 200
     * And RESPONSE BODY should contain "George"
     * And Headers Content Type is JSON
     */


    String endpoint = "https://reqres.in/api/users/";
    @DisplayName("GET all users")
    @Test
    public void usersGetTest () {
        //When user sends GET request to
        //Response response = RestAssured.get(endpoint);
        //Response response = get(endpoint); // since we did the static import, I can use the get() directly.
        Response response = when().get(endpoint); // This one and one above they do same thing. Just for GHERKIN language format we used when();


        // Then RESPONSE STATUS CODE is 200
        System.out.println("Status Code: " + response.statusCode());
        assertEquals(200, response.statusCode());

        // BDD Syntax - GHERKIN syntax
        response.then().statusCode(200); // This will do the exact same validation as one above
        response.then().assertThat().statusCode(200);  // This will also do the exact same validation as one above

        // And RESPONSE BODY should contain "George"

        // Difference between prettyPrint and asSting method
        // Pretty print shows it in JSON format
        // asString shows it in a single line
        System.out.println("Print the RESPONSE BODY: " + response.asString());
        response.prettyPrint();

        assertTrue(response.asString().contains("George"));


        // And Headers Content Type is JSON
        System.out.println("Response Content Type: " + response.contentType());
        // Response Content Type: application/json; charset=utf-8
        assertTrue(response.contentType().contains("application/json"));


    }

    /** NEW TEST CASE
     When User Sends get request to API Endpoint:
     "https://reqres.in/api/users/5"
     ---------------------------------------------------------
     Then Response status code should be 200
     And Response body should contain user info "Charles"
     And Response Header's Content Type is application/json
     */


    @DisplayName("GET single user")
    @Test
    public void getSingleUserApiTest () {

        Response response = when().get(endpoint + 5); // Concatenation

        System.out.println("Response Status Code: " + response.statusCode());
        assertEquals(200, response.statusCode());


        // And Response body should contain user info "Charles"
        response.prettyPrint();
        assertTrue(response.body().asString().contains("Charles"));


        //     And Response Header's Content Type is application/json
        System.out.println("Headers Content Type: " + response.contentType());
        assertTrue(response.contentType().contains("application/json"));


    }


    /** NEW TEST CASE
     When User Sends get request to API Endpoint:
     "https://reqres.in/api/users/50"
     ---------------------------------------------------------
     Then Response status code should be 404
     And Response body should contain {}
     */

    @DisplayName("GET request to non existing user")
    @Test
    public void getSingleUserNegativeApiTest (){
        Response response = when().get(endpoint + 50);
        System.out.println("Status Code: " + response.statusCode());
        assertEquals(404, response.statusCode());

        System.out.println("Response Body: " + response.body().asString());
        assertEquals("{}", response.asString());
    }


}
