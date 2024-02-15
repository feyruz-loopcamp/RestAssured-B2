package io.loopcamp.test.day10_a_specifications;

import io.loopcamp.utils.MinionSecureTestBase;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
public class MinionSpecTest extends MinionSecureTestBase {

    RequestSpecification requestSpec = given().accept(ContentType.JSON)
            .and().auth().basic("loopacademy", "loopacademy");


    ResponseSpecification responseSpec = expect().statusCode(200)
            .and().contentType(ContentType.JSON);

    @Test
    public void allMinionTest () {

//        given().accept(ContentType.JSON)
//                .and().auth().basic("loopacademy", "loopacademy")
        given().spec(requestSpec)
                .when().get("/minions")
                .then().spec(responseSpec)
                .log().all();
//                .then().assertThat().statusCode(200)
//                .and().contentType(ContentType.JSON);
    }

    @Test
    public void singleMinionTest () {
//        given().accept(ContentType.JSON)
//                .and().auth().basic("Loopacademy2", "Loopacademy2")
        given().spec(requestSpec)
                .and().pathParam("id", 10)
                .when().get("/minions/{id}")
                .then().spec(responseSpec)
//                .then().assertThat().statusCode(200)
//                .and().contentType(ContentType.JSON)
                .and().body("name", equalTo("Lorenza"));
    }

}
