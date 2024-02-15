package io.loopcamp.test.day06_b_post_put_delete;

import com.github.javafaker.Faker;
import io.loopcamp.pojo.Minion;
import io.loopcamp.utils.MinionRestUtils;
import io.loopcamp.utils.MinionTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MinionPostThenGet extends MinionTestBase {

    //Minion newMinion = make a method that returns a new minion with auto generated values
    Minion newMinion = MinionRestUtils.getNewMinion();


    @Test
    public void postNewMinionThenGetTest () {
        System.out.println("New Minion Info: " + newMinion);


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newMinion)  //SERIALIZATION - > from POJO object to JSON / parsing
                .when().post("/minions"); // POST request

        response.prettyPrint();
        assertThat(response.statusCode(), is(201));

        int newMinionID = response.jsonPath().getInt("data.id");

        Response response1 = given().accept(ContentType.JSON)
                .and().pathParam("id", newMinionID)
                .when().get("/minions/{id}"); // GET request

        System.out.println("GET request response: ");
        response1.prettyPrint();

        // DESERIALIZATION -- > convert the JSON response to Minion POJO class
        Minion minionFromGET = response1.as(Minion.class);

        // Compare the POSTed MINION with the one from GET MINION
        assertThat(minionFromGET.getName(), equalTo(newMinion.getName()));
        assertThat(minionFromGET.getGender(), equalTo(newMinion.getGender()));
        assertThat(minionFromGET.getPhone()+"", equalTo(newMinion.getPhone()+""));


        // Once I am done with the assertions, I can DELETE
        MinionRestUtils.deleteMinionById(newMinionID);


    }





}
