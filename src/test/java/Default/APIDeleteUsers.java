package Default;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIDeleteUsers {

	
	@Test
	public void Test1()
	{

		
		RequestSpecification request =RestAssured.given().auth().oauth2("012ea51f39d01740051121657412bb85dcb4ed7e30810c33623bd6dbdf5a2a9b")
				.header("Content-Type","application/json");
		
		
		JSONObject json =new JSONObject();
		json.put("id", "1");
		json.put("name", "Charbel Hanna9021");
		json.put("email", "ch9021@test.com");
		json.put("gender", "male");
		json.put("status", "active");
		
		request.body(json.toJSONString());
		Response resp= request.post("https://gorest.co.in/public/v1/users/");
		int code = resp.getStatusCode();
		System.out.println(resp.getBody().asString());
		System.out.println(resp.jsonPath().get("data.id"));
		//System.out.println("Id: "+id);
		Assert.assertEquals(code, 201);
		

	}
	
}


