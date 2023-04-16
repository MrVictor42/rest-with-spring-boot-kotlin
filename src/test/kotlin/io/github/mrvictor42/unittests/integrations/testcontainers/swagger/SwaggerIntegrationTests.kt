package io.github.mrvictor42.unittests.integrations.testcontainers.swagger

import io.github.mrvictor42.unittests.integrations.testcontainers.AbstractIntegrationTest
import io.github.mrvictor42.unittests.integrations.testcontainers.ConfigTests
import io.restassured.RestAssured
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTests() : AbstractIntegrationTest() {

    @Test
    fun shouldDisplaySwaggerUiPage() {
        val content =
            RestAssured
                .given()
                .basePath("/swagger-ui/index.html")
                .port(ConfigTests.SERVER_PORT)
                    .`when`()
                .get()
                .then()
                    .statusCode(200)
                .extract()
                .body()
                    .asString()
        Assertions.assertTrue(content.contains("Swagger UI"))
    }
}