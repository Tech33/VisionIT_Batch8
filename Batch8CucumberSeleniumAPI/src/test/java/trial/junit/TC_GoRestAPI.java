package trial.junit;

import org.junit.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TC_GoRestAPI {
	
	String server = "https://gorest.co.in/";
	String accessToken = "QdyszPfFpUufpBi6u2BDNpSvVaVDO-r2NU-O";
	
	@Test
	public void t_01_get_request() {
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.when()
				.get("/public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message",equalTo("OK. Everything worked as expected."))
				.extract()
				.response();

		System.out.println(resp.toString());
		
	}

	@Test
	public void t_02_negative_get_request_invalid_token() {
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2("Invalid Tokensdfsfggfdg")
				.when()
				.get("/public-api/users")
				.then()
				.assertThat()
				.body("_meta.success", equalTo(false))
				.body("_meta.code", equalTo(401))
				.body("_meta.message",equalTo("Authentication failed."))
				.body("result.name",equalTo("Unauthorized"))
				.body("result.message",equalTo("Your request was made with invalid credentials."))
				.extract()
				.response();
		
		System.out.println(resp.asString());
		
	}
	@Test
	public void t_03_get_request_validate_pagination() {
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.queryParam("page", "5")
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message",equalTo("OK. Everything worked as expected."))
				.body("_meta.currentPage", equalTo(5))
				.body("result", not(emptyArray()))
				.extract()
				.response();
		System.out.println("Response Returned as: " + resp.asString());
	}

	@Test
	public void t_04_get_request_single_user() {
		String user_id = "362";
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users/" + user_id)
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message",equalTo("OK. Everything worked as expected."))
				.body("result", not(emptyArray()))
				.body("result.id", equalTo(user_id))
				.extract()
				.response();
		System.out.println("Response Returned as: " + resp.asString());
	}
	@Test
	public void t_05_get_negative_incorrect_user() {
		String user_id = "345345353535353544";
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users/" + user_id)
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(false))
				.body("_meta.code", equalTo(404))
				.body("_meta.message",equalTo("The requested resource does not exist."))
				.body("result.name", equalTo("Not Found"))
				.body("result.message", equalTo("Object not found: " + user_id))
				.body("result.code", equalTo(0))
				.body("result.status", equalTo(404))
				.extract()
				.response();
		System.out.println("Response Returned as to: " + resp.asString());
	}
	@Test
	public void t_06_get_all_users_with_gender_as_female_and_status_as_active() {
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.queryParam("gender", "female")
				.queryParam("status", "active")
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users" )
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message",equalTo("OK. Everything worked as expected."))
				.body("result.gender", everyItem(equalTo("female")))
				.body("result.status", everyItem(equalTo("active")))
				.extract()
				.response();

		
		System.out.println("Response Returned as: " + resp.asString());

	}
	
}
