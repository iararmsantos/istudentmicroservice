package com.iarasantos.studentservice.integrationtests.swagger;

import com.iarasantos.studentservice.configs.TestConfigs;
import com.iarasantos.studentservice.integrationtests.testcontainers.AbstractingIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractingIntegrationTest {

    @Test
    public void shouldDisplaySwaggerUiPage() {
        var content =
            given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body().asString();
        assertTrue(content.contains("Swagger UI"));
    }
}
