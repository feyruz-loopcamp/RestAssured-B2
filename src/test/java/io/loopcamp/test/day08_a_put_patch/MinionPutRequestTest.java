package io.loopcamp.test.day08_a_put_patch;

import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 Given accept type is json
 And content type is json
 And path param id is 180
 And json body is
     {
         "gender": "Male",
         "name": "PutRequest",
         "phone": 1234567425
     }
 When I send PUT request to /minions/{id}
 Then status code 204
 */

public class MinionPutRequestTest extends MinionTestBase {


    @Test
    public void updateMinionTest () {

        Map <String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Male");
        requestMap.put("name", "PutRequest");
        requestMap.put("phone", 1234567425);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", 180)
                .and().body(requestMap)
                .when().put("/minions/{id}");

        response.prettyPrint(); // Since the response does not have any BODY, it will not print anything.

        System.out.println("Status Code: " + response.statusCode()); // 204
        assertThat(response.statusCode(), is(HttpStatus.SC_NO_CONTENT));

    }
















}
