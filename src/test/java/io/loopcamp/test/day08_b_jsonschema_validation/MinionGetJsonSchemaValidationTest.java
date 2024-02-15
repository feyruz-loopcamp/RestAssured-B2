package io.loopcamp.test.day08_b_jsonschema_validation;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class MinionGetJsonSchemaValidationTest extends MinionTestBase {
    /**
     given accept type is json
     and path param id is 15
     when I send GET request to /minions/{id}
     Then status code is 200
     And json payload/body matches SingleMinionSchema.json
     */

    @Test
    public void singleMinionSchemaValidationTest() {

        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/minions/{id}")
                .then().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                      new File("src/test/resources/jsonschemas/SingleMinionSchema.json")
                ))
                .and().log().all();
    }

    /**
     given accept type is json
     when I send GET request to /minions
     Then status code is 200
     And json payload/body matches AllMinionsSchema.json
     */


    @Test
    public void allMinionsSchemaValidationTest () {
        given().accept(ContentType.JSON)
                .when().get("/minions")
                .then().statusCode(200)
                .and().body(
                        JsonSchemaValidator.matchesJsonSchema(
                                new File("src/test/resources/jsonschemas/AllMinionSchema.json")
                        )
                )
                .log().all();

    }


    /**
     given accept type is json
     And query params: nameContains "e" and gender "Female"
     when I send GET request to /Minions/search
     Then status code is 200
     And json payload/body matches SearchMinionsSchema.json
     */

    @Test
    public void searchMinionSchemaValidationTest() {
        given().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .and().queryParam("gender", "Female")
                .and().get("/minions/search")
                .then().assertThat().statusCode(200)
                .and().body(
                        JsonSchemaValidator.matchesJsonSchema(
                                new File("src/test/resources/jsonschemas/SearchMinionsSchema.json")
                        )
                ).log().all();

    }














}
