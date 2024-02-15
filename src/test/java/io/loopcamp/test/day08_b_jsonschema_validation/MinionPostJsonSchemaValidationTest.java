package io.loopcamp.test.day08_b_jsonschema_validation;

import io.loopcamp.utils.MinionRestUtils;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

/**
     given accept is json
     and content type is json
     and body is:
         {
             "gender": "Male",
             "name": "TestPost1"
             "phone": 1234560000
         }
     When I send POST request to /minions
     Then status code is 201
     And body should match MinionPostSchema.json schema
 */
public class MinionPostJsonSchemaValidationTest extends MinionTestBase {


    @Test
    public void postMinionSchemaTest (){

        Map <String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Male");
        requestMap.put("name", "TestPost1");
        requestMap.put( "phone",  1234560000);


        int id = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post("/minions")
                .then().assertThat().statusCode(HttpStatus.SC_CREATED)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/MinionPostSchema.json")
                ))
                .and().log().all()
                .and().extract().jsonPath().getInt("data.id");


        System.out.println("Newly added minion id: " + id);
        MinionRestUtils.deleteMinionById(id);


    }


}

















