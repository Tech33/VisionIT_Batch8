package myapitrail.junit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import io.restassured.response.Response;

public class PutRequestTC 
{
	String Server="https://gorest.co.in";
	String accesstoken = "NhVSgzOi4JxXPvBS3POKn1sMmStxE5EnJ6gP";
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
	/*public List<Integer> Get_AllUserIdList()
	{
		Response rep=given().relaxedHTTPSValidation()
				.baseUri(Server)
				.auth().oauth2(accesstoken)
				.when()
				.get("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.success", equalTo("OK. Everything worked as expected."))
				.extract()
				.response();
		List<Integer> lis = new ArrayList<Integer>();
		lis = rep.jsonPath().getList(Server);
		System.out.println("user id as:" +lis);
		return (lis);
	}*/
	public int Create_user_and_return_id()	{
		String gender_type ="female";
		String lastName = GetRandomString(8);
		String firstName = GetRandomString(5);
		String email_id = GetRandomString(10)+"@gmail.com";
		String body_spetext ="{\"gender\":\"" + gender_type + "\",\"last_name\":\""+lastName+"\",\"first_name\":\""+firstName+"\",\"email\":\""+email_id+"\"}";
		System.out.println("Body Text Snt As:" + body_spetext);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
    	Response rep = given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_header)
				.auth().oauth2(accesstoken)				
				.when()
				.body(body_spetext)
				.post("/public-api/users")
				.then()
				.assertThat()
				//in this test case i use the 200 status code that time test case is failed
				.statusCode(302)
				.body("_meta.code", equalTo(201))
				.body("_meta.success",equalTo(true))
				.body("_meta.message", equalTo("A resource was successfully created in response to a POST request. The Location header contains the URL pointing to the newly created resource."))
				.extract()
				.response();
		System.out.println("Response returned as:" + rep.asString());
		int id = rep.jsonPath().getInt("result.id");
		System.out.println("user id is::" + id);
		return id;
		}
	
	@Test
	public void t_01_Put_UpdateExistingUser_Negative_TryToUpdateUserWhichIsNotPresent()	
	{
		int user_id = Create_user_and_return_id();		
		String email_id = GetRandomString(10)+"@gmail.com";
		System.out.println("email_id: "+email_id);
		String body_spetext ="{\"email\":\""+email_id+"\"}";
		System.out.println("Body Text Snt As:" + body_spetext);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
    	Response rep = given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_header)
				.auth().oauth2(accesstoken)				
				.when()
				.body(body_spetext)
				.put("/public-api/users/"+user_id)
				.then()
				.assertThat()
				//in this test case i use the 200 status code that time test case is failed
				.statusCode(200)
				.body("_meta.code", equalTo(200))
				.body("_meta.success",equalTo(true))
				.body("_meta.message", equalTo("OK. Everything worked as expected."))
				.extract()
				.response();
		System.out.println("Response returned as:" + rep.asString());
		
		}
	
	@Test
	public void t_02_Put_UpdateExisting_user_SendIncoorectvlueToTheFeild()	
	{
		int user_id = Create_user_and_return_id();		
		String email_id = GetRandomString(10)+"gmil.com";
		System.out.println("email_id: "+email_id);
		String body_spetext ="{\"email\":\""+email_id+"\"}";
		System.out.println("Body Text Snt As:" + body_spetext);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
    	Response rep = given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_header)
				.auth().oauth2(accesstoken)				
				.when()
				.body(body_spetext)
				.put("/public-api/users/"+user_id)
				.then()
				.assertThat()
				//in this test case i use the 200 status code that time test case is failed
				.statusCode(200)
				.body("_meta.code", equalTo(422))
				.body("_meta.success",equalTo(false))
				.body("_meta.message", equalTo("Data validation failed. Please check the response body for detailed error messages."))
				.body("result[0].field", equalTo("email"))
				.body("result[0].message", equalTo("Email is not a valid email address."))
				.extract()
				.response();
		System.out.println("Response returned as:" + rep.asString());
		
		}
	@Test
	public void t_03_Delete_User_Positive()
	{
		int user_id = Create_user_and_return_id();
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
		Response rep = given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_header)
				.auth().oauth2(accesstoken)				
				.when()
				.delete("/public-api/users/"+user_id)
				.then()
				.assertThat()
				.statusCode(200)
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(204))
				.body("_meta.message", equalTo("The request was handled successfully and the response contains no body content."))
				.extract()
				.response();
		System.out.println("Response retruned as:" +rep.asString());
		}
	@Test
	public void t_04_PutUpdateExistingUser_Positive()
	{
		String firstname = "jhade";
		System.out.println("email_id: "+firstname );
		String body_spetext ="{\"first_name\":\""+firstname +"\"}";
		System.out.println("Body Text Snt As:" + body_spetext);
		HashMap<String,String> hm_header = new HashMap<String,String>();
		hm_header.put("content-type", "application/json");
		System.out.println("Header"+ hm_header.toString());
		Response resp=given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_header)
				.auth().oauth2(accesstoken)
				.when()
				.body(body_spetext)
				.put("public-api/users/338")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(true))
				.body("_meta.code", equalTo(200))
				.body("_meta.message", equalTo("OK. Everything worked as expected."))
				.extract()
				.response();
		System.out.println("Response retruned as:" +resp.asString());
				
	}
}
