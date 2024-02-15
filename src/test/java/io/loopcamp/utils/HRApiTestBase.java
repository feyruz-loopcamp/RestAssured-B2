package io.loopcamp.utils;

import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.baseURI;

public class HRApiTestBase {

    @BeforeAll  // JUnite - @Before
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("hr.api.url"); // Since we are doing static import from RestAssured, we can use baseUri which helps us for GET request concatenation in our test
    }
}
