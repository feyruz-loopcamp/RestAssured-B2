package io.loopcamp.test.day04_a_json_path;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionJsonPathTest extends MinionTestBase {

    /**
          Given accept is json
          And path param id is 13
          When I send get request to /minions/{id}
          ----------
          Then status code is 200
          And content type is json
          And id value is 13
          And name is "Jaimie"
          And gender is "Female"
          And phone is "7842554879"
     */


    @DisplayName("GET /minions/{id}")
    @Test
    public void getMinionJsonPathTest () {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/minions/{id}");

        System.out.println("Status Code: " + response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        System.out.println("Content type: " + response.contentType());
        assertEquals("application/json", response.contentType());
        // how to get it using the header method.
        assertEquals(ContentType.JSON.toString(), response.getHeader("Content-Type"));


        /**
            {
                "id": 13,
                "gender": "Female",
                "name": "Jaimie",
                "phone": "7842554879"
            }
         */


        // parse/assign path to jsonPath
        JsonPath jsonPath = response.jsonPath();
        System.out.println("id: " + jsonPath.getInt("id"));
        System.out.println("gender: " + jsonPath.getString("gender"));
        System.out.println("name: " + jsonPath.getString("name"));
        System.out.println("phone: " + jsonPath.getString("phone"));

        assertEquals(13, jsonPath.getInt("id"));
        assertEquals("Female", jsonPath.getString("gender") );
        assertEquals("Jaimie", jsonPath.getString("name") );
        assertEquals("7842554879", jsonPath.getString("phone") );
    }

}
