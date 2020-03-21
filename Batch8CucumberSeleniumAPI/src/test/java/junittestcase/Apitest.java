package junittestcase;

import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Apitest {

    String servername="http://dummy.restapiexample.com/";
	
	@Test
	public void get_request1()
	{
		
		given().baseUri("http://dummy.restapiexample.com/").
		when().get("/api/v1/employees").
		then().
		assertThat().statusCode(200).
		assertThat().body("status",equalTo("success")).
		assertThat().body("data[0].id",equalTo("1")).
		assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
		
	}
	

	@Test
	public void get_request2_breaking_steps()
	{
		RequestSpecification req_spec=given().baseUri(servername);
	Response response=	req_spec.when().get("/api/v1/employees");
	ValidatableResponse  valid_response=response.then();
	
		valid_response.assertThat().statusCode(200).
		assertThat().body("status",equalTo("success")).
		assertThat().body("data[0].id",equalTo("1")).
		assertThat().body("data[0].employee_name",equalTo("Tiger Nixon"));
		
	}
	
	
}
