package io.loopcamp.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.baseURI;

public class MinionTestBase {
    @BeforeAll  // JUnite - @Before
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("minion.api.url"); // Since we are doing static import from RestAssured, we can use baseUri which helps us for GET request concatenation in our test
    }
}
