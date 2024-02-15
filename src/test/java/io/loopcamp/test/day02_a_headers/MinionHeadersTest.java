package io.loopcamp.test.day02_a_headers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionHeadersTest {
    /**
     • When I send a GET request to --- > minions_base_url/api/minions -- > your_id:8000 = minions_base_url
        -------------
     • Then Response STATUS CODE must be 200
     • And I Should see all Minions data in XML format
     */

    String requestUrl = "http://18.206.236.92:8000/api/minions";
    @DisplayName("GET /api/minions and expect defaulted XML format")
    @Test
    public void getAllMinionsHeadersTest (){
        when().get(requestUrl)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.XML); // This will be more dynamic instead of putting "application/xml"
                //.and().contentType("application/xml");

    }

    /**
     * Given Accept type is application/json
     • When I send a GET request to
     • minions_base_url/api/minions
     -----------------------------
     • Then Response STATUS CODE must be 200
     • And I Should see all Minions data in json format
     */

    @DisplayName("GET /api/minions with requested header JSON")
    @Test
    public void acceptTypeHeaderTest () {
        //given().accept("application/json")
        given().accept(ContentType.JSON)        // More dynamic using ENUMs
                .when().get(requestUrl)
                .then().assertThat().statusCode(200)
                //.and().contentType("application/json")
                .and().contentType(ContentType.JSON);  // More dynamic using ENUMs

    }



    /**
     * Given Accept type is application/xml
     • When I send a GET request to  --- >minion_base_url/api/minions
     -----------------------------
     • Then Response STATUS CODE must be 200
     • And read headers //(how to read a specific header)
     */

    @DisplayName("GET /api/minions with requested header JSON - read headers")
    @Test
    public void readResponseHeadersTest() {
        Response response = given().accept(ContentType.JSON)
                .when().get(requestUrl);

        //response.prettyPrint();
        assertEquals(200, response.statusCode());

        // How to read each header - .getHeader(String str - Key);
        System.out.println("Date Header: " + response.getHeader("Date"));
        System.out.println("Content-type Header: " + response.getHeader("Content-Type"));
        System.out.println("Connection Header: " + response.getHeader("Connection"));


        // How to read all Headers - .getHeaders();
        System.out.println("\nAll Headers: \n" + response.getHeaders());

        // How would validate if any of the header is not empty
        assertTrue(response.getHeader("Keep-Alive") != null);

    }
}
