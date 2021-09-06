package Default;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
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

	

	
	@Test
	public void GetUserPass() throws SQLException, IOException
	{
		System.out.println(getTokenMethod());
		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=1 and name is not null and email is not null and gender is not null and status is not null ORDER BY RAND() limit 1");
	 	
	 	int id = 0;
	 	
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 	}

		Response resp= request.get(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 200);
		

	}
	
	@Test
	public void GetUserFail() throws SQLException, IOException
	{
		System.out.println(getTokenMethod());
		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
				
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=0");
	 	
	 	int id = 0;
	 	
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 	}

		Response resp= request.get(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 404);
		
	}
	
	
}
