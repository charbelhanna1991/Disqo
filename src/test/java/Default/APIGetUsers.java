package Default;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pageObject.LandingPage;
import pageObject.LoginPage;
import pageObject.RedirectionPage;
import pageObject.TokenPage;
import resources.Base;

public class APIGetUsers extends Base{

	String Token;
	
	@BeforeSuite
	public void login() throws IOException
	{

		
		initializeDriver();
		getDriver().get(getWeb());
		
		
		
		LandingPage l = new LandingPage(getDriver());
		l.getlogin().click();
		
		LoginPage lp = new LoginPage(getDriver());
		lp.getloginGithub().click();
		
		RedirectionPage rp = new RedirectionPage(getDriver());
		rp.getusername().sendKeys(getUsername());
		rp.getpassword().sendKeys(getPassword());
		rp.getlogin().click();
		
		TokenPage tp = new TokenPage(getDriver());
		this.Token = tp.gettoken().getText();
	}
	
	@Test
	public void Test1()
	{
		
		RequestSpecification request =RestAssured.given().auth().oauth2(this.Token)
				.header("Content-Type","application/json");
		
		
		JSONObject json =new JSONObject();
		json.put("id", "1");
		json.put("name", "Charbel Hanna9025");
		json.put("email", "ch9025@test.com");
		json.put("gender", "male");
		json.put("status", "active");
		
		request.body(json.toJSONString());
		Response resp= request.post(getWeb()+"public/v1/users");
		int code = resp.getStatusCode();
		System.out.println(resp.getBody().asString());
		System.out.println(resp.jsonPath().get("data.id"));
		//System.out.println("Id: "+id);
		Assert.assertEquals(code, 201);
		

	}
	
	@AfterTest
	public void close() 
	{
		getDriver().close();
		
	}
}
