	package trail.junit.api.gorest;

	import org.junit.Test;

	import io.restassured.response.Response;
	//import io.restassured.response.ValidatableResponse;
	//import io.restassured.specification.RequestSpecification;


	import static io.restassured.RestAssured.*;
	import static org.hamcrest.Matchers.*;

public class TC_GetRequest {
		
		String server = "https://gorest.co.in/";
		String accessToken = "yC_ASJI4461M33rysgw28LipoOhjOgjDUhXt";
		
		@Test
		public void t_01_get_request_fetch_all_users() {
			
			System.out.println("\n <-------------- Executing Test Case : t_01_get_request_fetch_all_users -------------->");
			
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
							.body("result", not(emptyArray()))
							.extract()
							.response();
			 
			System.out.println("Response returned as: " + resp.asString());
		}
 		
		@Test
		public void t_02_get_request_validate_pagination() {
			
			System.out.println("\n <-------------- Executing Test Case : t_02_get_request_validate_pagination -------------->");
			
			Response resp = given()
							.baseUri(server)
							.queryParam("page", "5")
							.auth().oauth2(accessToken)
							.when()
							.get("/public-api/users")
							.then()
							.assertThat()
							.statusCode(200)
							.body("_meta.success", equalTo(true))
							.body("_meta.code", equalTo(200))
							.body("_meta.message", equalTo("OK. Everything worked as expected."))
							.body("result", not(emptyArray()))
							.body("_meta.currentPage", equalTo(5))
							.extract()
							.response();
			 
			System.out.println("Response returned as: " + resp.asString());
		}
		
		@Test
		public void t_03_get_request_fetch_single_user_details() {
			
			System.out.println("\n <-------------- Executing Test Case : t_03_get_request_fetch_single_user_details -------------->");
			
			String user_id = "133";
			
			Response resp = given()
							.baseUri(server)
							.auth().oauth2(accessToken)
							.when()
							.get("/public-api/users/" + user_id)
							.then()
							.assertThat()
							.statusCode(200)
							.body("_meta.success", equalTo(true))
							.body("_meta.code", equalTo(200))
							.body("_meta.message", equalTo("OK. Everything worked as expected."))
							.body("result", not(emptyArray()))
							.body("result.id", equalTo("133"))
							.extract()
							.response();
			 
			System.out.println("Response returned as: " + resp.asString());
		}
		
		@Test
		public void t_04_get_request_negative_fetch_incorrect_user_details() {
			
			System.out.println("\n <-------------- Executing Test Case : t_04_get_request_negative_fetch_incorrect_user_details -------------->");
			
			String user_id = "764625342364274723";
			
			Response resp = given()
							.baseUri(server)
							.auth().oauth2(accessToken)
							.when()
							.get("/public-api/users/" + user_id)
							.then()
							.assertThat()
							.statusCode(200)
							.body("_meta.success", equalTo(false))
							.body("_meta.code", equalTo(404))
							.body("_meta.message", equalTo("The requested resource does not exist."))
//							.body("result", not(emptyArray()))
							.body("result.name", equalTo("Not Found"))
							.body("result.message", equalTo("Object not found: " + user_id))
							.body("result.code", equalTo(0))
							.body("result.status", equalTo(404))
							.extract()
							.response();
			 
			System.out.println("Response returned as: " + resp.asString());
		}
		
		@Test		
		public void t_05_get_request_fetch_all_users_with_gender_as_female() {
			
			System.out.println("\n <-------------- Executing Test Case : t_05_get_request_fetch_all_users_with_gender_as_female -------------->");
			
			Response resp = given()
							.baseUri(server)
							.queryParam("gender", "female")
							.auth().oauth2(accessToken)
							.when()
							.get("/public-api/users")
							.then()
							.assertThat()
							.statusCode(200)
							.body("_meta.success", equalTo(true))
							.body("_meta.code", equalTo(200))
							.body("_meta.message", equalTo("OK. Everything worked as expected."))
							.body("result.gender", everyItem(equalTo("female")))
							.extract()
							.response();
			 
			System.out.println("Response returned as: " + resp.asString());
		}

		@Test		
		public void t_06_get_request_fetch_all_users_with_gender_as_female_and_status_as_active() {
	
			System.out.println("\n <-------------- Executing Test Case : t_06_get_request_fetch_all_users_with_gender_as_female_and_status_as_active -------------->");
	
			Response resp = given()
							.baseUri(server)
							.queryParam("gender", "female")
							.queryParam("status", "active")
							.auth().oauth2(accessToken)
							.when()
							.get("/public-api/users")
							.then()
							.assertThat()
							.statusCode(200)
							.body("_meta.success", equalTo(true))
							.body("_meta.code", equalTo(200))
							.body("_meta.message", equalTo("OK. Everything worked as expected."))
							.body("result.gender", everyItem(equalTo("female")))
							.body("result.status", everyItem(equalTo("active")))
							.extract()
							.response();
	 
			System.out.println("Response returned as: " + resp.asString());
}
}
