package io.loopcamp.test.day04_b_xml_path_deserialization;


import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionToMapTest extends MinionTestBase {

    /**
         Given accept type is application/json
         And Path param id = 10
         When I send GET request to /minions
                --------
         Then status code is 200
         And content type is json
         And minion data matching:
             id > 10
             name>Lorenza
             gender >Female
             phone >3312820936
     */



    @DisplayName("GET /minions/{id}")
    @Test
    public void minionToMapTest () {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("/minions/{id}");
        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        /*
            {
                "id": 10,
                "gener": "Female",
                "name": "Lorenza",
                "phone": "3312820936"
            }
        */
        // CONVERT JSON Response body to Java Collection Object [MAP]
        //Map <String, Object> minionMap = response.as(Map.class);
        Map <String, Object> minionMap = response.body().as(Map.class);  // same as above
        System.out.println("Minion in Map: " + minionMap);
        System.out.println("All the keys: " + minionMap.keySet());   //id, gender, name, phone
        System.out.println("All the values: " + minionMap.values()); //10, Female, Lorenza, 3312820936



        assertEquals(10, minionMap.get("id"));
        assertEquals("Female", minionMap.get("gender"));
        assertEquals("Lorenza", minionMap.get("name"));
        assertEquals("3312820936", minionMap.get("phone"));

    }
}













