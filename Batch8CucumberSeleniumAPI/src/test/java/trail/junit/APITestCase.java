package trail.junit;

import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Random;
public class APITestCase {
	
	String server_name = "http://dummy.restapiexample.com/";
	@Test
	public void t_01_get_request() {
		
		System.out.println(" <-------------- Executing Test Case : t_01_get_request -------------->");
		
		given().baseUri("http://dummy.restapiexample.com/").when().get("/api/v1/employees").then().
		assertThat().statusCode(200).
		assertThat().body("status", equalTo("success")).
		assertThat().body("data[0].id",equalTo("1")).
		assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
	}
	
	@Test
	public void t_02_get_request_breaking_steps() {
		
		System.out.println(" <-------------- Executing Test Case : t_02_get_request_breaking_steps -------------->");
		
		RequestSpecification req_spec = given().baseUri(server_name);
		Response response = req_spec.when().get("/api/v1/employees");
		ValidatableResponse valid_response = response.then();
		
		valid_response.assertThat().statusCode(200).
		assertThat().body("status", equalTo("success")).
		assertThat().body("data[0].id",equalTo("1")).
		assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
	}
	
	@Test
	public void t_03_get_get_single_employee_detail() {
		
		System.out.println(" <-------------- Executing Test Case : t_03_get_get_single_employee_detail -------------->");
		
		RequestSpecification req_spec = given().baseUri(server_name);
		Response response = req_spec.when().get("/api/v1/employee/1");
		ValidatableResponse valid_response = response.then();
		
		valid_response.assertThat().statusCode(200).
		assertThat().body("status", equalTo("success")).
		assertThat().body("data[0].id",equalTo("1")).
		assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
	}
	
	@Test
	public void t_04_get_request_get_incorrect_employee_detail() {
		
		System.out.println(" <-------------- Executing Test Case : t_04_get_request_get_incorrect_employee_detail -------------->");
		
		RequestSpecification req_spec = given().baseUri(server_name);
		Response response = req_spec.when().get("/api/v1/employee/456473764534gge4");
		ValidatableResponse valid_response = response.then();
		
		//To be valiidated. Need to check status code & json message with text as data not found
		Assert.assertTrue("Validation yet to be written, until then marked as failed", false);

		valid_response.assertThat().statusCode(200).
		assertThat().body("status", equalTo("success"));
	}
	
	@Test
	public void t_05_post_positive_post_create_new_employee() {
		
		System.out.println(" <-------------- Executing Test Case : t_05_post_positive_post_create_new_employee -------------->");
		
		String dynamic_name = GetRandomString(8);
		
		//Body Text
		String body_text = "{\"name\":\"" + dynamic_name + "\",\"salary\":\"123\",\"age\":\"23\"}";
		
		System.out.println("Body Text sent as: " + body_text);
		
		//Header Hash Map
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("Content-Type", "application/json");
		
		System.out.println("Header sent as: " + hm_header.toString());
		
		//Request Specification with Base URI and header
		RequestSpecification req_spec = given().baseUri(server_name).headers(hm_header);
		
		//Getting the response of Post, Note: body is also sent in the Post request
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		System.out.println("Response received after Post Request" + response.asString());
		ValidatableResponse valid_response = response.then();
		
		//Assertion 1
		valid_response.assertThat().body("status", equalTo("success")).
		assertThat().body("data.name",equalTo(dynamic_name)).
		assertThat().body("data.salary",equalTo("123")).
		assertThat().body("data.age",equalTo("23"));
		
		//another validation to check id
		int id = response.jsonPath().getInt("data.id");
		//String salary = response.jsonPath().getString("data.salary");
		
		//Map<String, String> hm_data = response.jsonPath().getMap("data");
		
		Response response_get = given().baseUri("http://dummy.restapiexample.com/").when().get("/api/v1/employee/"+ id);
		System.out.println("Response received after Get Request" + response.asString());
		response_get.then().
		assertThat().statusCode(200).
		assertThat().body("status", equalTo("success")).
		assertThat().body("data.id",equalTo(id)).
		assertThat().body("data.employee_salary",equalTo(123)).
		assertThat().body("data.employee_age",equalTo(23)).
		assertThat().body("data.employee_name",equalTo(dynamic_name));
	}
	
	//To get random Key
	public String GetRandomString(int n) {
		//lower limit for lower case letters
		int lowerlimit = 97;
		
		//Upper limit for lower case letters
		int upperlimit = 122;
		
		Random random = new Random();
		
		//Create a String Buffer to store the result
		StringBuffer r = new StringBuffer(n);
		
		for(int i = 0; i <= n; i++)
		{
			//take a random value between 97 nd 122
			int nextRandomChar = lowerlimit
					+ (int)(random.nextFloat() 
							* (upperlimit - lowerlimit +1));
			
			//append a character at the end of bs
			r.append((char)nextRandomChar);
		}
		
		//return to resultant String
		return r.toString();
	}
	
	
	@Test
	public void t_06_post_negative_send_incorrect_name() {
		
		System.out.println(" <-------------- Executing Test Case : t_06_post_negative_send_incorrect_name -------------->");
		
		String dynamic_name = "-99832656586";
		
		//Body Text
		String body_text = "{\"name\":\"" + dynamic_name + "\",\"salary\":\"123\",\"age\":\"23\"}";
		
		System.out.println("Body Text sent as: " + body_text);
		
		//Header Hash Map
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("Content-Type", "application/json");
		
		System.out.println("Header sent as: " + hm_header.toString());
		
		//Request Specification with Base URI and header
		RequestSpecification req_spec = given().baseUri(server_name).headers(hm_header);
		
		//Getting the response of Post, Note: body is also sent in the Post request
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		System.out.println("Response received after Post Request" + response.asString());
		ValidatableResponse valid_response = response.then();
		
		//Assertion 1
		//valid_response.assertThat().body("status", not(equalTo("success")));
		ValidatableResponse vresponse = valid_response.assertThat().body("status", equalTo("failed"));
		
		//System.out.println(" Test Case : t_06_post_negative_send_incorrect_name Passed successfully");
	}
	
	@Test
	public void t_07_post_negative_send_negative_salary() {
		System.out.println(" <-------------- Executing Test Case : t_07_post_negative_send_negative_salary -------------->");
		
		String dynamic_name = GetRandomString(8);
		
		//Body Text
		String body_text = "{\"name\":\"" + dynamic_name + "\",\"salary\":\"-238534\",\"age\":\"23\"}";
		
		System.out.println("Body Text sent as: " + body_text);
		
		//Header Hash Map
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("Content-Type", "application/json");
		
		System.out.println("Header sent as: " + hm_header.toString());
		
		//Request Specification with Base URI and header
		RequestSpecification req_spec = given().baseUri(server_name).headers(hm_header);
		
		//Getting the response of Post, Note: body is also sent in the Post request
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		System.out.println("Response received after Post Request" + response.asString());
		ValidatableResponse valid_response = response.then();
		
		//Assertion 1
		//valid_response.assertThat().body("status", not(equalTo("success")));
		ValidatableResponse vresponse = valid_response.assertThat().body("status", equalTo("failed"));
	}
	
	@Test
	public void t_08_post_negative_send_negative_age() { 
		System.out.println(" <-------------- Executing Test Case : t_08_post_negative_send_negative_age -------------->");
		
		String dynamic_name = GetRandomString(8);
		
		//Body Text
		String body_text = "{\"name\":\"" + dynamic_name + "\",\"salary\":\"123\",\"age\":\"-26\"}";
		
		System.out.println("Body Text sent as: " + body_text);
		
		//Header Hash Map
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("Content-Type", "application/json");
		
		System.out.println("Header sent as: " + hm_header.toString());
		
		//Request Specification with Base URI and header
		RequestSpecification req_spec = given().baseUri(server_name).headers(hm_header);
		
		//Getting the response of Post, Note: body is also sent in the Post request
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		System.out.println("Response received after Post Request" + response.asString());
		ValidatableResponse valid_response = response.then();
		
		//Assertion 1
		//valid_response.assertThat().body("status", not(equalTo("success")));
		ValidatableResponse vresponse = valid_response.assertThat().body("status", equalTo("failed"));

	}
	
	@Test
	public void t_09_post_negative_send_special_characters() {
		System.out.println(" <-------------- Executing Test Case : t_09_post_negative_send_special_characters -------------->");
		
		String dynamic_name = "!@#$%^&*()_+-=?><:";
		
		//Body Text
		String body_text = "{\"name\":\"" + dynamic_name + "\",\"salary\":\"!@#$%^&*()_+-=?><:\",\"age\":\"!@#$%^&*()_+-=?><:\"}";
		
		System.out.println("Body Text sent as: " + body_text);
		
		//Header Hash Map
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("Content-Type", "application/json");
		
		System.out.println("Header sent as: " + hm_header.toString());
		
		//Request Specification with Base URI and header
		RequestSpecification req_spec = given().baseUri(server_name).headers(hm_header);
		
		//Getting the response of Post, Note: body is also sent in the Post request
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		System.out.println("Response received after Post Request" + response.asString());
		ValidatableResponse valid_response = response.then();
		
		//Assertion 1
		//valid_response.assertThat().body("status", not(equalTo("success")));
		ValidatableResponse vresponse = valid_response.assertThat().body("status", equalTo("failed"));
	}
}