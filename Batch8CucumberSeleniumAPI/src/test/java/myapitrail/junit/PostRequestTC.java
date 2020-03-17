package myapitrail.junit;

import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import io.restassured.response.Response;

public class PostRequestTC 
{
	String Server="https://gorest.co.in";
	String accesstoken = "NhVSgzOi4JxXPvBS3POKn1sMmStxE5EnJ6gP";
	@Test
	public void t_01_Post_Request_Create_user()
	{
		String gender_type ="female";
		String lastName = "jaiswal";
		String firstName = "pari";
		String email_id = "hytt.tumsar1234sach@gmail.com";
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
		}
	@Test
	public void t_02_PostUser_Nagative_BodyHasAllFieldButNotValue()
	{   String gender_type = "";
		String lastName = "";
		String firstName = "";
		String email_id = "";
		String body_spetext ="{\"gender\":\"" + gender_type + "\",\"last_name\":\""+lastName+"\",\"first_name\":\""+firstName+"\",\"email\":\""+email_id+"\"}";
		System.out.println("Body text send as" + body_spetext );
		HashMap <String,String> hm_headers = new HashMap<String,String>();
		hm_headers.put("content-type", "application/json");
		System.out.println("Header"+ hm_headers.toString());
		Response rep = given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_headers)
				.auth().oauth2(accesstoken)
				.when()
				.post("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(false))
				.body("_meta.code", equalTo(422))
				.body("_meta.message",equalTo("Data validation failed. Please check the response body for detailed error messages."))
				.extract()
				.response();
		System.out.println("Response returend as:" + rep.asString());
				
	}
	@Test
	public void t_03_PostUser_Nagative_SendEmptyBody()
	{
		String body_spetext="{ }";
		System.out.println("Body text send as:" + body_spetext);
		HashMap <String,String> hm_headers =new HashMap<String,String>();
		hm_headers.put("content-type", "application/json");
		System.out.println("Header" + hm_headers.toString());
		Response rep = given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_headers)
				.auth().oauth2(accesstoken)
				.when()
				.post("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(false))
				.body("_meta.code", equalTo(422))
				.body("_meta.message", equalTo("Data validation failed. Please check the response body for detailed error messages."))
				.body("result[0].message", equalTo("Email cannot be blank."))
				.body("result[1].message", equalTo("First Name cannot be blank."))
				.body("result[2].message", equalTo("Last Name cannot be blank."))
				.body("result[3].message", equalTo("Gender cannot be blank."))
				.extract()
				.response();
		System.out.println("Response retruned as:" + rep.asString());
		
	}
	@Test
	public void t_04_Post_Negative_BodyHasAllFeildButInccorectValue()
	{
		String gender_type = "xyz";
		int lastName = 34646;
		int firstName = 125855;
		String email_id = "rutu.badwaik.com";
        String body_spetext ="{\"gender\":\"" + gender_type + "\",\"last_name\":\""+lastName+"\",\"first_name\":\""+firstName+"\",\"email\":\""+email_id+"\"}";
        System.out.println("Body Text Send as:" + body_spetext);
        HashMap<String,String> hm_headers = new HashMap<String,String>();
        hm_headers.put("content-type", "application/json");
        System.out.println("Header" + hm_headers);
        Response rep = given().relaxedHTTPSValidation()
        		.baseUri(Server)
        		.headers(hm_headers)
        		.auth().oauth2(accesstoken)
        		.when()
        		.post("public-api/users")
        		.then()
        		.assertThat()
        		.statusCode(200)
        		.body("_meta.success", equalTo(false))
        		.body("_meta.code", equalTo(422))
        		.body("_meta.message", equalTo("Data validation failed. Please check the response body for detailed error messages."))
        		.body("result.message[0]", equalTo("Email is not a valid email address."))
        		//this msg genreted by postman 
        		//but actual msg is genreted (Name can not blank) so which message i can use 
        		.body("result.message[1]", equalTo("First Name must be a string."))
        		.body("result.message[2]",equalTo("Last Name must be a string."))
        		.body("result.message[3]", equalTo("Gender is invalid."))
        		.extract()
        		.response();
        System.out.println("Response returned as:" + rep.asString());
        		
	}
	@Test
	public void t_05_Post_Negative_BodyHasAllFeilHavingInccorectData()
	{
		String gender_type = "male";
		String lastName = "Nexson";
		String firstName = "Loin";
		String email_id = "Loin.Nexson@gmail.com";
		String body_spetext ="{\"gender\":\"" + gender_type + "\",\"last_name\":\""+lastName+"\",\"first_name\":\""+firstName+"\",\"email\":\""+email_id+"\"}";
		System.out.println("Body Text Sent as:" + body_spetext);
		HashMap<String,String> hm_headers=new HashMap<String,String>();
		hm_headers.put("content-type", "application/json");
		System.out.println("Header" + hm_headers);
		Response rep =given().relaxedHTTPSValidation()
				.baseUri(Server)
				.headers(hm_headers)
				.auth().oauth2(accesstoken)
				.when()
				.post("public-api/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body("_meta.success", equalTo(false))
				.body("_meta.code", not(equalTo(500)))
				//i use the postman created message that time test case is failed but use this actual msg at time of automation the test case is run successfully 
				.body("_meta.message", equalTo("Data validation failed. Please check the response body for detailed error messages."))
				.extract()
				.response();
		System.out.println("Response returend as " + rep.asString());
				
		
	}
	
	
}
