package io.loopcamp.test.day04_a_json_path;

import io.loopcamp.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZipCodeApiJsonPathTest {

/**
     ***Zip code task.  http://api.zippopotam.us/us/22031
     Given Accept application/json
     And path zipcode is 22031
     When I send a GET request to /us endpoint
        --------------------
     Then status code must be 200
     And content type must be application/json
     And Server header is cloudflare
     And Report-To header exists
     And body should contains following information
        post code is 22031
        country  is United States
        country abbreviation is US
        place name is Fairfax
        state is Virginia
        latitude is 38.8604
 */


    @BeforeEach
    public void setUp () {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @DisplayName("GET /us/zipcode")
    @Test
    public void zipCodeJsonPathTest () {

        Response response = given().accept(ContentType.JSON)
                .pathParam("country", "us")
                .pathParam("zipcode", "22031")
                .when().get("/{country}/{zipcode}");

       //response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // assigning response json payload/body to JsonPath
        JsonPath jsonPath = response.jsonPath();


        System.out.println("Post Code: " + jsonPath.getString("'post code'")); // If you have a space in the KEY, you need to use single quotes
        System.out.println("Country: " + jsonPath.getString("country")); // If you have NO a space in the KEY, you DO NOT need to use single quotes
        //response.path("country");

        assertEquals("22031", jsonPath.getString("'post code'"));

        // calling the method which will verify if the passes or not.
        verifyZipCode("22301", jsonPath);

        assertEquals("United States", jsonPath.getString("country"));

        // country abbreviation
        System.out.println("Country Abbreviation: " + jsonPath.getString("'country abbreviation'")); //country abbreviation
        assertEquals("US", jsonPath.getString("'country abbreviation'"));



        assertEquals("Fairfax", jsonPath.getString("places[0].'place name'"));


        // State abbreviation
        System.out.println("State Abbreviation: " + jsonPath.getString("places[0].'state abbreviation'")); //state abbreviation

        System.out.println("State name: " + jsonPath.getString("places[0].state"));
        assertEquals("Virginia", jsonPath.getString("places[0].state"));

        System.out.println("Latitude: " + jsonPath.getString("places[0].latitude"));
        assertEquals("38.8604", jsonPath.getString("places[0].latitude"));



    }

    // Since we can to all with .path() method as well, why we do it with jsonPath ?
    // 1. jsonPath and some more methods will help us to filter the result directly in the assertions -- which we will see later.
    // 2. we can use json path when we want to call a reusable method.

    public void verifyZipCode (String expZipCode, JsonPath jsonPath) {
        assertEquals("22031", jsonPath.getString("'post code'"));
    }

}
