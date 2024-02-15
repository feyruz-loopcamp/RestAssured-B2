package io.loopcamp.test.day05_deserialization;


import io.loopcamp.pojo.Minion;
import io.loopcamp.pojo.MinionSearch;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionSearPojoTest extends MinionTestBase {


    /**
     Given accept type is json
     And query param nameContains value is "e"
     And query param gender value is "Female"
     When I send get request to /minions/search
            --------
     Then status code is 200
     And content type is Json
     And json response data is as expected
     */

    @Test
    public void minionSearchPojoTest () {

//        given().accept(ContentType.JSON)
//                .and().queryParam("nameContains", "e")
//                .and().queryParam("gender", "Female")
//                .when().get("/minions/search");


//        given().accept(ContentType.JSON)
//                .and().queryParams("nameContains", "e", "gender", "Female")
//                .when().get("/minions/search");

        Map <String, String> queryParams = new HashMap<>();
        queryParams.put("nameContains", "e");
        queryParams.put("gender", "Female");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryParams)
                .when().get("/minions/search");

        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());


        // DESERIALIZE JSON RESPONSE BODY to MINIONSEARCH pojo class
        MinionSearch minionSearch = response.body().as(MinionSearch.class);


        // how can you get the total elements.
        System.out.println("Total Elemeents: " + minionSearch.getTotalElement());


        // all minions
        System.out.println("All minions: \n" + minionSearch.getContent() );


        // get me the first minion info
        System.out.println("First minion info: " + minionSearch.getContent().get(0));



        // Cna you only print all the names
        for (Minion each : minionSearch.getContent()) {
            System.out.println(each.getName());
        }









    }


}
