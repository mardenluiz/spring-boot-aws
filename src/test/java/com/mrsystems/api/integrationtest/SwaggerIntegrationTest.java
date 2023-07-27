package com.mrsystems.api.integrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mrsystems.api.config.TestConfigs;
import com.mrsystems.api.integrationtest.testcontainers.AbstractIntegrationTest;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void contextLoads() {
		var content = RestAssured.given()
			.basePath("/swagger-ui/index.html")
			.port(TestConfigs.SERVER_PORT)
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		
		Assertions.assertTrue(content.contains("Swagger UI"));
	}

}
