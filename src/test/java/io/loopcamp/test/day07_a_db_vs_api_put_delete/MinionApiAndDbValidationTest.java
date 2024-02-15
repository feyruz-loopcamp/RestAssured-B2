package io.loopcamp.test.day07_a_db_vs_api_put_delete;

import io.loopcamp.pojo.Minion;
import io.loopcamp.utils.ConfigurationReader;
import io.loopcamp.utils.DBUtils;
import io.loopcamp.utils.MinionRestUtils;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionApiAndDbValidationTest extends MinionTestBase {
    /**
     given accept is json
     and content type is json
     and body is:
         {
             "gender": "Male",
             "name": "PostVSDatabase",
             "phone": 1234567425
         }
     When I send POST request to /minions
     Then status code is 201
     And content type is "application/json;charset=UTF-8"
     And "success" is "A Minion is Born!"
     When I send database query
         SELECT name, gender, phone
         FROM minions
         WHERE minion_id = newIdFrom Post request;
     Then name, gender , phone values must match with POST request details
     */
    Minion neeMin = MinionRestUtils.getNewMinion();
    @Test
    public void postNewMinionThenValidateInDBTest() {
        Map <String, Object> newMinion = new HashMap<>();
        newMinion.put("gender", "Male");
        newMinion.put("name",  "PostVSDatabase");
        newMinion.put("phone", 1234567425);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newMinion) // SERIALIZATION - MAP > JSON
                .when().post("/minions");

        response.prettyPrint();
        assertThat(response.statusCode(), is(HttpStatus.SC_CREATED));
        assertThat(response.contentType(), equalTo("application/json;charset=UTF-8"));

        //assertThat(response.path("success"), equalTo("A Minion is Born!")); // with path()

        JsonPath jsonPath = response.jsonPath(); // converting path to jsonPath

        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!")); // with jsonPath.getString()

        int newMinionID = jsonPath.getInt("data.id");
        System.out.println("Newly added Minions ID: " + newMinionID);

        /*
         When I send database query
             SELECT name, gender, phone
             FROM minions
             WHERE minion_id = newIdFrom Post request;
         */

        String query = "SELECT name, gender, phone FROM minions WHERE minion_id = " + newMinionID;

        String dbUrl = ConfigurationReader.getProperty("minion.db.url");
        String username = ConfigurationReader.getProperty("minion.db.username");
        String password = ConfigurationReader.getProperty("minion.db.password");


        // create connection
        DBUtils.createConnection(dbUrl, username, password);
        Map <String, Object > dbMap = DBUtils.getRowMap(query);
        System.out.println("MINION Info From DB: " + dbMap);


        assertThat(dbMap.get("GENDER"), equalTo(newMinion.get("gender")));
        assertThat(dbMap.get("NAME"), equalTo(newMinion.get("name")));
        assertThat(dbMap.get("PHONE")+"", equalTo(newMinion.get("phone")+""));


        MinionRestUtils.deleteMinionById(newMinionID);
        DBUtils.destroy();

    }
}
