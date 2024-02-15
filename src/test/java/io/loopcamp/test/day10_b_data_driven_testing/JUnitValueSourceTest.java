package io.loopcamp.test.day10_b_data_driven_testing;

import io.loopcamp.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
public class JUnitValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {15, 23, 45, 23, 7, 34, 675, 34})
    public void numberTest (int num) {

        System.out.println("Num: " + num);
        assertThat(num, is(greaterThan(10)));

    }


    @ParameterizedTest
    @ValueSource(strings = {"Emil", "Elyas", "Tom", "Jerry", "Vinnie"})
    public void testNames (String names) {

        System.out.println("Hi " + names);
        assertThat(names, not(blankOrNullString()));

    }


    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @ValueSource(ints = {22192, 98033, 77493, 37650, 77044})
    //22192, 98033, 77493, 37650, 77044
    public void zipcodeTest(int zipCode) {

        given().accept(ContentType.JSON)
                .and().pathParam("postal_code", zipCode)
                .when().get("/US/{postal_code}")
                .then().assertThat().statusCode(200)
                .log().all();

    }


}



