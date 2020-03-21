package trial.junit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

import io.restassured.response.Response;

public class GoRestAPITestCase {

	/*
	 * . Get Request /public-api/users
	 * . Get Request /public-api/users/123
	 * . Get Request /public-api/users?first_name=john
	 * . Get Request /public-api/users?status=inactive
	 * . Get Request /public-api/users?gender=female&&status=inactive
	 */
	
	@Test
	public void t_01_get() {
		Response resp = given().relaxedHTTPSValidation().
		baseUri("https://gorest.co.in/")
		.auth().oauth2("QdyszPfFpUufpBi6u2BDNpSvVaVDO-r2NU-O")
		.queryParam("gender", "female")
		.when()
		.get("/public-api/users")
		.then().assertThat().body("result.gender", everyItem(equalTo("female"))).extract().response();
		
		List<String> result = resp.jsonPath().getList("result.first_name");

		
		System.out.println(resp.asString());
		
	}
}


