package io.loopcamp.test.day09_a_authorization;

import io.loopcamp.utils.MinionSecureTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionBasicAuthTest extends MinionSecureTestBase {
    /**
         given accept type is json
         and basic auth credentials are loopacademy , loopacademy
         When user sends GET request to /minions
         Then status code is 200
         And content type is json
         And print response
     */


    @Test
    public void getMinionWithAuthTest () {

        given().accept(ContentType.JSON)
                .and().auth().basic("loopacademy", "loopacademy")
                .when().get("/minions")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .log().all()
                .and().extract();
    }



    /**
     * [NEGATIVE - RAINY]
     given accept type is json

     When user sends GET request to /minions
     Then status code is 401
     And content type is json
     And error is "Unauthorized"
     And log response
     */

    @Test
    public void getAllMinionsUnAuthorizedTEst() {
        given().accept(ContentType.JSON)
                .when().get("/minions")
                .then().statusCode(401)
                .and().contentType(ContentType.JSON)
                .and().body("error", equalTo("Unauthorized"))
                .log().all();
    }

}



















