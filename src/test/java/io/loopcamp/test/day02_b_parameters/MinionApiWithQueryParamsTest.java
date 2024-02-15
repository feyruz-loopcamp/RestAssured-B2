package io.loopcamp.test.day02_b_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionApiWithQueryParamsTest {

    /**
         Given Accept type is Json
         And query parameter values are:
         gender|Female
         nameContains|e
         When user sends GET request to /api/minions/search
            ---------------------------
         Then response status code should be 200
         And response content-type: application/json
         And "Female" should be in response payload
         And "Janette" should be in response payload
     */
    String endpoint = "http://18.206.236.92:8000/api/minions/search";

    @DisplayName(" GET /api/minions/search with querry params")
    @Test
    public void searchForMinionTest () {
        //Response response = given().log().all().accept(ContentType.JSON)
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get(endpoint);

        // verify is the status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        // verify response header content-typ
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // And "Female" should be in response payload
        // And "Janette" should be in response payload
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(!response.body().asString().contains("Male")); // true
        assertTrue(response.body().asString().contains("Janette"));

    }



}
