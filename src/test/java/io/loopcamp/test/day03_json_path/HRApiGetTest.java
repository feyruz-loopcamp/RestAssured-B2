package io.loopcamp.test.day03_json_path;

import io.loopcamp.utils.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class HRApiGetTest {
    /**
     * User should be able to see all regions when sending GET request to /ords/hr/regions
     *      Given accept type is json
     *      When user send get request to /regions
     *          -----------
     *      Status code should be 200
     *      Content type should be "application/json"
     *      And body should contain "Europe"
     */




    @BeforeEach  // JUnite - @Before
     public void setUp () {
        //String url = ConfigurationReader.getProperty("hr.api.url");
        baseURI = ConfigurationReader.getProperty("hr.api.url"); // Since we are doing static import from RestAssured, we can use baseUri which helps us for GET request concatenation in our test
    }


    @DisplayName("GET /regions")
    @Test
    public void getRegionsTest(){

        // .log().all() --- >  can be used for request or response to show all the details in console.
        /** given().log().all().accept(ContentType.JSON)
                .when().get(url + "/regions")
                 .then().log().all().assertThat().statusCode(200);
         */


        Response response = given().accept(ContentType.JSON)
                .when().get("/regions");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

    }

    /**
         Given accept type is json
         And Path param "region_id" value is 10
         When user send get request to /ords/hr/regions/{region_id}
         Status code should be 200
         Content type should be "application/json"
         And body should contain "Europe"
     */
    @DisplayName("GET /regions/{region_id}")
    @Test
    public void getSingleRegionPathParamTest(){
        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("region_id", 10)
                .when().get("/regions/{region_id}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

    }


    /**
         * Given accept type is json
         * And query param q={"region_name": "Americas"}
         * When user send get request to /regions
         *          --------
         * Status code should be 200
         * Content type should be "application/json"
         * And region name should be "Americas"
         * And region id should be "20"
     */


    @DisplayName("GET /regions?q={\"region_name\": \"Americas\"}")
    @Test
    public void getRegionQueryParamTest(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_name\": \"Americas\"}")
                .when().get("/regions");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Americas"), "does NOT contain region_name");
        assertTrue(response.body().asString().contains("20"), "does NOT contain region_id");

    }


}
