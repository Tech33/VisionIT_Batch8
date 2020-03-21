package junittestcase;

import org.junit.Test;

import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;

public class APItestcase {

	String url="http://dummy.restapiexample.com/";
	@Test
	public void api_get_request()
	{
		Response resp=given().baseUri(url).
		when().get("/api/v1/employees").andReturn();
		String body=resp.asString();

//		System.out.println("Response Body:" + body);
		//Assertion for status code
		
		int statusCodeActual= resp.getStatusCode();
		int statusCodeExpected=200;
		//Assert.assertEquals(expected, actual);
		
		System.out.println("status cod returned:"+ statusCodeActual);
		
		if(body.contains("success"))
		{
			System.out.println("Tc passed");
			//Assert.assertTrue("Tc passed",True);
			
		}
		else
		{
			System.out.println("Tc failed");
		}
		
	}
	
	
}
