package io.loopcamp.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DocuportApiTestBase {

    protected static RequestSpecification requestSpec;

    protected static ResponseSpecification responseSpec;



    @BeforeAll  // JUnite - @Before
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("docuport.base.url"); // Since we are doing static import from RestAssured, we can use baseUri which helps us for GET request concatenation in our test


        String token = getAccessToken("employee");

        requestSpec = given().accept(ContentType.JSON)
                .and().header("Authorization", token);


        responseSpec = expect().statusCode(200)
                .and().contentType(ContentType.JSON);

    }





    //Make a method that gets the token
    public static String getAccessToken (String username) {

        String jsonBody = "{\n" +
                "\"usernameOrEmailAddress\": \"b1g3_" + username + "@gmail.com\",\n" +
                "\"password\": \""+ ConfigurationReader.getProperty("docuport.global.password") +"\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/api/v1/authentication/account/authenticate");

        String accessToken = response.path("user.jwtToken.accessToken");

        assertTrue(accessToken != null && !accessToken.isEmpty());

        return "Bearer " + accessToken;
    }








}
