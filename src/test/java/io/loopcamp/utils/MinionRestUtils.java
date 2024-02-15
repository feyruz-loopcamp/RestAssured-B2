package io.loopcamp.utils;

import com.github.javafaker.Faker;
import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionRestUtils {

    private static String baseUrl = ConfigurationReader.getProperty("minion.api.url");

    public static void deleteMinionById (int id) {

        System.out.println("DELETING minion with id {" + id + "}");
        given().accept(ContentType.JSON)
                .and().pathParam("id", id)
                .when().delete(baseUrl + "/minions/{id}");
                //.then().log().all();
    }


    /**
     * This method returns newly generated Minion with random data
     * @return new minion
     */
    public static Minion getNewMinion () {

        // create a Faker class object to be able to generate random values
        Faker random =new Faker();

        Minion minion = new Minion();
        minion.setName(random.name().firstName());
        //minion.setPhone(random.phoneNumber().cellPhone()); // you can do substring manipulation to match the format -- > 1-112-418-2302 - >  we need xxxxxxxxxx
        //minion.setPhone(random.number().numberBetween(1000000000, 9999999999L)+"");
        minion.setPhone(random.numerify("##########"));
        int num = random.number().numberBetween(1, 3); // random number between 1 and 2.
        if (num == 1) {
            minion.setGender("Female");
        } else {
            minion.setGender("Male");
        }

        return minion;
    }

    /**
     * Method accepts minion ID and sends GET request
     * @param minionID
     * @return is MAP containing info of minion.
     */

    public static  Map <String, Object>  getMinionById(int minionID) {


        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", minionID)
                .when().get(baseUrl + "/minions/{id}");

        //Minion min = response.as(Minion.class);
        Map <String, Object> minionMap = response.as(Map.class);

        return  minionMap;

    }
}
