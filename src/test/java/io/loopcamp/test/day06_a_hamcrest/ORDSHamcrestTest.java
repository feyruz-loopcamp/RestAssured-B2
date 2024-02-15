package io.loopcamp.test.day06_a_hamcrest;

import io.loopcamp.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSHamcrestTest extends HRApiTestBase {

    /**
     * given accept type is json
     * when I send get request to /countries
     *      ---------
     * Then status code is 200
     * and content type is json
     * and count should be 25
     * and country ids should contain "AR,AU,BE,BR,CA"
     * and country names should have "Argentina,Australia,Belgium,Brazil,Canada"
     *
     * items[0].country_id ==> AR
     * items[1].country_id ==> AU
     */


    String country_id = "";
    @DisplayName("GET /countries -- with Hamcrest")
    @Test
    @Order(1)
    public void countriesTest () {

        country_id = given().accept(ContentType.JSON)
                .when().get("/countries")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("count", is(25),
                        "items.country_id", hasItems("AR", "AU", "BE", "BR", "CA"),
                        "items.country_name", hasItems("Argentina", "Australia" ,"Belgium", "Brazil", "Canada"),
                        "items[0].country_id", is(equalTo("AR")),
                        "items[1].country_id", is("AU"))
                .and().extract().body().path("items[0].country_id");
                //.log().all();

        System.out.println("First Country Id: "  + country_id);



        given().accept(ContentType.JSON)
                .and().pathParam("country_id", country_id)
                .when().get("/countries/{country_id}")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("country_name", is("Argentina"),
                        "country_id", is(country_id),
                        "region_id", is(20));


    }


    //TODO:  DO the research how can the order be used with the JUnit.
    /**
     *   * given accept type is json
     *      * when I send get request to /countries/{country_id}
     *      * Then status code is 200
     *      * and content type is json
     *      And country_name is Argentina, country_id is AR, region_id is 20
     */
//    @DisplayName("GET /countries/{country_id}")
//    @Test
//    @Order(2)
//    public void singleCountryTest () {
//        given().accept(ContentType.JSON)
//                .and().pathParam("country_id", country_id)
//                .when().get("/countries/{country_id}")
//                .then().statusCode(200)
//                .and().contentType(ContentType.JSON)
//                .and().body("country_name", is("Argentina"),
//                        "country_id", is("AR"),
//                        "region_id", is(20));
//    }


}
























