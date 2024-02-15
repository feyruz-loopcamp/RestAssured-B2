package io.loopcamp.test.day05_deserialization;

import io.loopcamp.pojo.Minion;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class AllMinionsPojoTest extends MinionTestBase {

    /**
         Given accept type is json
         when I send GET request to /minions
            ----
         Then status code is 200
         And content type is json
         And I can convert json to list of minion pojos
     */


    @Test
    public void allMinionPojoTest () {

        Response response = given().accept(ContentType.JSON)
                .when().get("/minions");
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());

        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();

        List <Minion> allMinions = jsonPath.getList("", Minion.class); // "" -- > hey start from beginning in JSON response body - > like from here {

        List<Minion> allFemaleMinions = new ArrayList<>();

        for (Minion each : allMinions) {
            if (each.getGender().equals("Female")) {
                allFemaleMinions.add(each);
            }
        }

        System.out.println(allFemaleMinions);

        // How many Female Minion we have?
        System.out.println("Female minion count: " + allFemaleMinions.size());




    }


}
