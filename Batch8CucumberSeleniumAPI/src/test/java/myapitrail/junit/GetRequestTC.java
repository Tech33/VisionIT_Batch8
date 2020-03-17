package myapitrail.junit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import io.restassured.response.Response;


public class GetRequestTC 
{
	String server = "https://gorest.co.in/";
	String accessToken = "NhVSgzOi4JxXPvBS3POKn1sMmStxE5EnJ6gP";
	
	@Test
	public void t_01_get_request_fetch_all_users()
	{
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users")
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
	public void t_02_get_request_pagination()
	{
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.queryParam("page",5)
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message", equalTo("OK. Everything worked as expected."))
				.body("_meta.currentPage", equalTo(5))
				.body("result", not(emptyArray()))
				.extract()
				.response();
				System.out.println("Response retured as:" + resp.asString());
		}
	
	
	@Test
	public void t_03_get_request_single_user()
	{
		String User_id = "157";
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users/" + User_id)
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message", equalTo("OK. Everything worked as expected."))
				.body("result", not(emptyArray()))
				.body("result.id", equalTo(User_id))
				.extract()
				.response();
				System.out.println("Response retured as:" + resp.asString());
				
	}
	
	@Test
	public void t_04_get_request_negative_incorrect_user()
	{
		String User_name ="3568906442134566";
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.when()
				.get("public-api/users/" + User_name)
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(false))
				.body("_meta.code", equalTo(404))
				.body("_meta.message", equalTo("The requested resource does not exist."))
				.body("result.name", equalTo("Not Found"))
				.body("result.message", equalTo("Object not found: 3568906442134566"))
				.body("result.code", equalTo(0))
				.body("result.status", equalTo(404))
				.extract()
				.response();
				
				System.out.println("Response returned as:" + resp.asString());
	}
	
	@Test
	public void t_05_get_request_all_users_as_gender_as_female()
	{
		Response resp = given().relaxedHTTPSValidation()
				.baseUri(server)
				.auth().oauth2(accessToken)
				.queryParam("gender", "female")
				.when()
				.get("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message",equalTo("OK. Everything worked as expected."))
				.body("result.gender", everyItem(equalTo("female")))
				.extract()
				.response();
				
				System.out.println("Response returned as: " + resp.asString());
		
	}
	
		@Test
		public void t_06_get_request_all_users_as_gender_as_female_and_status_active()
		{
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
