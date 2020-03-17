package myapitrail.junit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class FirstWeekAssigment {
	
	String server_name="http://dummy.restapiexample.com";
	@Test
	public void t_01_get_request()
	{
		given().baseUri("http://dummy.restapiexample.com")
		.when().get("/api/v1/employees")
		.then()
		.assertThat().statusCode(200)
		.assertThat().body("status",equalTo("success"))
		.assertThat().body("data[0].id",equalTo("1"))
		.assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
		
	}
	
	@Test
	public void t_02_get_request_breaking_steps()
	{
		RequestSpecification req_spec =given().baseUri(server_name);
		Response response = req_spec.when().get("/api/v1/employees");
		ValidatableResponse valid_response = response.then();
		valid_response.assertThat().statusCode(200)
		.assertThat().body("status",equalTo("success"))
		.assertThat().body("data[0].id",equalTo("1"))
		.assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
		
	}
	
	@Test
	public void t_03_get_request_get_single_employee_detail() {
		RequestSpecification req_spec = given().baseUri(server_name);
		Response response = req_spec.when().get("/api/v1/employee/1");
		ValidatableResponse valid_response = response.then();
		valid_response.assertThat().statusCode(200).
		//assertThat().body("status",equalTo("success")).
		assertThat().body("data[0].id",equalTo("1")).
		assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
	}
	
	@Test
	public void t_04_get_request_get_incorrect_employee_detail() {
		RequestSpecification req_spec = given().baseUri(server_name);
		Response response = req_spec.when().get("/api/v1/employee/45644454646645654645646egeg");
		ValidatableResponse valid_response = response.then();
		valid_response.assertThat().statusCode(200).
		assertThat().body("status",not(equalTo("success")));

	}
		
	
	//POST TEST CASE
	@Test
	public void t_05_post_postive_create_new_employee()
	{
		String dynamic_name = GetRandomString(8);
		String body_text =  "{\"name\":\"" + dynamic_name + "\",\"salary\":\"123\",\"age\":\"23\"}";
		System.out.println("Body Text sent as: " + body_text);
		
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("Content-Type", "application/json");
		System.out.println("Header " + hm_header.toString());
		
		RequestSpecification req_spec =given().baseUri(server_name).headers(hm_header);
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		System.out.println("Response received after Post Request " + response.asString());
		ValidatableResponse valid_response = response.then();
		
		//Assertion 1
				valid_response.assertThat().body("status",equalTo("success")).
				assertThat().body("data.name",equalTo(dynamic_name)).
				assertThat().body("data.salary",equalTo("123")).
				assertThat().body("data.age",equalTo("23"));
		//Assertion 2
				int id = response.jsonPath().getInt("data.id");
				Response response_get = given().relaxedHTTPSValidation().baseUri(server_name).when().get("/api/v1/employee/" + id);
				System.out.println("Response received after Get Request: " + response_get.asString());
				
				response_get.then().
				assertThat().statusCode(200).
				assertThat().body("status",equalTo("")).
				assertThat().body("data.id",equalTo(id)).
				assertThat().body("data.employee_name",equalTo(dynamic_name)).
				assertThat().body("data.employee_salary",equalTo("123")).
				assertThat().body("data.employee_age",equalTo("23"));


	}
	
	@Test
	public void t_06_post_postive_create_new_employee()
	{
		String dynamic_name = "23467887";
		String body_text ="{\"name\":\"" + dynamic_name + "\",\"salary\":\"123\",\"age\":\"23\"}";
		
		System.out.println("Body Text sent as:"+ body_text);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
		RequestSpecification req_spec = given().relaxedHTTPSValidation().basePath("server_name").headers(hm_header);
		Response response = req_spec.when().body(body_text).post("/api/v1/create");
		
		System.out.println("Response received after Post Request " + response.asString());
		ValidatableResponse valid_response = response.then();
		//Assertion 1
		valid_response.assertThat().body("status",not(equalTo("success")));
		valid_response.assertThat().body("status",(equalTo("failed")));
	}
	@Test
	public void t_07_post_negative_send_incorrect_negative_salary()
	{
		String dynamic_salary = "-123455";
		String body_sal="{\"name\":\"xyz\",\"salary\":\""+ dynamic_salary + "\",\"age\":\"23\"}";
		System.out.println("Body Text sent as:" + body_sal);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
		RequestSpecification req_spec = given().relaxedHTTPSValidation().basePath("server_name").headers(hm_header);
		Response response = req_spec.when().body(body_sal).post("/api/v1/create");
		System.out.println("Response recived after post Request" + response.asString());
		ValidatableResponse valid_response = response.then();
		//Assertion 1
		valid_response.assertThat().body("status",not(equalTo("success")));
		valid_response.assertThat().body("status", (equalTo("failed")));
		}
	
	@Test
	public void t_08_post_negative_send_incorrect_negative_age() 
	{
		String dynamic_age ="-34";
		String body_age="{\"name\":\"xyz\",\"salary\":\"123\",\"age\":\""+dynamic_age + "\"}";
		System.out.println("Body Text Sent as:" + body_age);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
		RequestSpecification req_spec =given().basePath("server_name").headers(hm_header);
		Response response = req_spec.when().body(body_age).post("/api/v1/create");
		System.out.println("Response recived after post request" + response.asString());
		ValidatableResponse valid_response = response.then();
		//Assertion 1
		valid_response.assertThat().body("status",not(equalTo("success")));
		valid_response.assertThat().body("status", (equalTo("failed")));
	}

	
	@Test
	public void t_09_post_negative_send_special_characters() 
	{
		String dynamic_spename ="@@";
		String body_spetext ="{\"name\":\"" + dynamic_spename + "\",\"salary\":\"123\",\"age\":\"23\"}";
		System.out.println("Body Text Snt As:" + body_spetext);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
		RequestSpecification req_spec =given().relaxedHTTPSValidation().basePath("server_name").headers(hm_header);
		Response response =req_spec.when().body(body_spetext).post("/api/v1/create");
		System.out.println("Response recived after post request" + response.asString());
		ValidatableResponse valid_response =response.then();
		
		
	}
	
	
	
	public String GetRandomString(int n) {
		 
		int lowerLimit = 97; 

 
		int upperLimit = 122; 

		Random random = new Random(); 

	
		StringBuffer r = new StringBuffer(n); 

		for (int i = 0; i < n; i++) { 

			 
			int nextRandomChar = lowerLimit 
					+ (int)(random.nextFloat() 
							* (upperLimit - lowerLimit + 1)); 
 
			r.append((char)nextRandomChar); 
		} 

		 
		return r.toString(); 
	} 
	

}
