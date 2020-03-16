package trail.junit;

import org.junit.Test;

import io.restassured.response.Response;
//import io.restassured.response.ValidatableResponse;
//import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TC_GoRestAPI {
	String server = "https://gorest.co.in/";
	String accessToken = "yC_ASJI4461M33rysgw28LipoOhjOgjDUhXt";
	@Test
	public void t_01_get_request() {
		Response resp = given()
						.baseUri(server)
						.auth().oauth2(accessToken)
						.when()
						.get("/public-api/users")
						.then()
						.assertThat()
						.statusCode(200)
						.body("_meta.success", equalTo(true))
						.body("_meta.code", equalTo(200))
						.body("_meta.message", equalTo("OK. Everything worked as expected."))
						.extract()
						.response();
		 
		System.out.println(resp.asString());
	}
	
	@Test
	public void t_02_negative_get_request_invalid_token() {
		Response resp = given()
						.baseUri(server)
						.auth().oauth2("Invalid Token jdnfjhbsdhvbjhvb")
						.when()
						.get("/public-api/users")
						.then()
						.assertThat()
						.statusCode(200)
						.body("_meta.success", equalTo(false))
						.body("_meta.code", equalTo(401))
						.body("_meta.message", equalTo("Authentication failed."))
						.body("result.name", equalTo("Unauthorized"))
						.body("result.message", equalTo("Your request was made with invalid credentials."))
						.extract()
						.response();
		 
		System.out.println(resp.asString());
	}
} 
