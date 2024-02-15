package io.loopcamp.test.day04_b_xml_path_deserialization;


import io.loopcamp.pojo.Minion;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionToPojoTest extends MinionTestBase {

    /**
         Given accept type is application/json
         And Path param id = 10
         When I send GET request to /minions
             --------------
         Then status code is 200
         And content type is json
         And minion data matching:
             id > 10
             name>Lorenza
             gender >Female
             phone >3312820936
     */



    @Test
    public void minionToPojoTest () {
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

        // DESERIALIZATION - > from JSON RESPONSE BODY to JAVA CUSTOM CLASS -- > pojo class
        // Jackson and Lombok is doing all the work in the background.
        Minion minionObj = response.as(Minion.class);

        System.out.println("Id: " + minionObj.getId());
        System.out.println(minionObj);


        assertEquals(10, minionObj.getId());
        assertEquals("Female", minionObj.getGender());
        assertEquals("Lorenza", minionObj.getName());
        assertEquals("3312820936", minionObj.getPhone());




    }


}
