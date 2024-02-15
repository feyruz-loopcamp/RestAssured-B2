package io.loopcamp.test.day10_a_specifications;

import io.loopcamp.utils.DocuportApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
/**
     Given accept type is json
     And header Authorization is access_token of employee title
     When I send GET request to /api/v1/company/organizer-titles/all
     Then status code is 200
     And content type is json
     And employee title info is presented in payload
 */



public class DocuportSpecTest extends DocuportApiTestBase {


    @Test
    public void employeeInfoTest () {


//        List <Map <String, ? >> list = given().accept(ContentType.JSON)
//                .and().header("Authorization", token)
        List <Map <String, ? >> list = given().spec(requestSpec)
                .when().get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpec)
//                .then().assertThat().statusCode(200)
//                .and().contentType(ContentType.JSON)
                .and().extract().body().as(List.class);  // DESERIALIZATION


        System.out.println("list = " + list);

        assertEquals("1", list.get(0).get("value"));
        assertEquals("President", list.get(0).get("displayText"));


        // assert the first object in response payload by using hamcrest matchers
        given().spec(requestSpec)
                .when().get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpec)
                .and().body("value[0]", is("1"))
                .and().body("displayText[0]", is(equalTo("President")));




        //assert the all object in response payload by using hamcrest matchers
        given().spec(requestSpec)
                .when().get("/api/v1/company/organizer-titles/all")
                .then().spec(responseSpec)
                .and().body("displayText", hasItems("President","Vice President","Treasurer","Secretary"))
                .and().body("value", hasItems("1", "2", "3", "4"))
                .and().body("displayText", hasSize(4))
                .log().all();


    }
}
