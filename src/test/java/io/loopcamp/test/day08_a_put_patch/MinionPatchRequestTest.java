package io.loopcamp.test.day08_a_put_patch;

import io.loopcamp.utils.MinionRestUtils;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


/**
     Given accept type is json
     And content type is json
     And path param id is 179
     And json body is
         {
            "phone": 2024442288
         }
     When i send PATCH request to /minions/{id}
     Then status code 204
 */
public class MinionPatchRequestTest extends MinionTestBase {

    @Test
    public void minionPatchTest () {
        Map <String, Object> requestMap = new HashMap<>();
        requestMap.put("phone", "2024442288");

        int minionID = 179;

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", minionID)
                .and().body(requestMap)
                .when().patch("/minions/{id}")
                .then().assertThat(). statusCode(204)
                .and().assertThat().body(emptyString());


        Map <String, Object> minionMap = MinionRestUtils.getMinionById(minionID);
        System.out.println("Update Minion Phone: " + minionMap.get("phone"));

        assertThat(minionMap.get("phone")+"", is(requestMap.get("phone")+""));


    }


}
