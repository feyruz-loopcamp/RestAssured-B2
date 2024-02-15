package io.loopcamp.test.day03_json_path;

import io.loopcamp.utils.ConfigurationReader;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionPathMethodTest extends MinionTestBase {

    /**
         * Given accept is json
         * And path param id is 13
         * When I send get request to /minions/{id}
         * ----------
         * Then status code is 200
         * And content type is json
         * And id value is 13
         * And name is "Jaimie"
         * And gender is "Female"
         * And phone is "7842554879"
     */

    @DisplayName("GET /minions/{id}")
    @Test
    public void readMinionJsonUsingPathTest (){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/minions/{id}");
        response.prettyPrint();
        /*
            {
                "id": 13,
                "gender": "Female",
                "name": "Jaimie",
                "phone": "7842554879"
            }
         */
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        /*
         * And id value is 13
         * And name is "Jaimie"
         * And gender is "Female"
         * And phone is "7842554879"
         */
        //assertTrue(response.body().asString().contains("13"));
        System.out.println("id: " + response.path("id"));
        System.out.println("name: " + response.path("name"));
        System.out.println("gender: " + response.path("gender"));
        System.out.println("phone: " + response.path("phone"));


        Integer expId = 13;
        String name = response.path("name");

        /*
         * And id value is 13
         * And name is "Jaimie"
         * And gender is "Female"
         * And phone is "7842554879"
         */
        assertEquals(new Integer(13), response.path("id")); // Here both has to be same data type which is Integer.
        assertEquals(expId, response.path("id")); // Here both has to be same data type which is Integer.


        assertEquals("Jaimie", response.path("name")); // String
        assertEquals("Female", response.path("gender"));
        assertEquals("7842554879", response.path("phone"));


    }


    /**
         Given accept is json
         When I send get request to /minions
            -------------
         Then status code is 200
         And content type is json
         And I can navigate json array object
     */

    @DisplayName("GET /minions with path()")
    @Test
    public void readMinionJsonArrayUsingPathTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/minions");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        /** Sample response body part
         [
             {
                 "id": 1,
                 "gender": "Male",
                 "name": "Meade",
                 "phone": "9994128233"
             },
             {
                 "id": 2,
                 "gender": "Male",
                 "name": "Nels",
                 "phone": "4218971348"
            }
         ]
         */
        // Print all ids
        System.out.println( "All Ids: " + response.path("id"));
        System.out.println( "All Ids: " + response.path("name"));


        // Print 1st minion id and name
        System.out.println( "1st minion id: " + response.path("[0].id"));
        System.out.println( "1st minion id: " + response.path("id[0]")); // this will do the same thing.
        System.out.println( "1st minion id: " + response.path("name[0]"));


        // Print last minion id and name -- > we just need to provide -1
        System.out.println( "last minion id: " + response.path("id[-1]"));
        System.out.println( "last minion id: " + response.path("name[-1]"));
        // System.out.println( "1st minion id: " + response.path("[-1].id")); // This will not find it.


        // Where can we store all the ids
        List <Integer> listId = response.path("id");
        // How many minions I have?
        System.out.println("Total Minions: " + listId.size());
        System.out.println("All Ids: " + listId);

        // How can you store all the names into List -- > Print all names and say Hi --- ? Hi $name!
        List <String> listName = response.path("name");
        for (int i = 0; i < listName.size(); i++) {
            System.out.println( "Hi " + listName.get(i) + "!" );
        }


        //System.out.println();
        //for (String each : listName) {
        //    System.out.println("Hi " + each + "!");
        //}

        //System.out.println();
        //listName.forEach(each -> System.out.println("Hi " + each + "!"));



    }



























}
