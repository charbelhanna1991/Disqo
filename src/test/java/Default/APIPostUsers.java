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

public class APIPostUsers extends Base{

	
	
	@Test 
	public void PostUserPass() throws SQLException, IOException
	{
		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod()).header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=0 and name is not null and email is not null and gender is not null and status is not null ORDER BY RAND() limit 1");
	  	int id = 0;
	 	String name="",email="",gender="",status="";
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 		name=rs.getString("name");
	 		email=rs.getString("email");
	 		gender=rs.getString("gender");
	 		status=rs.getString("Status");
	 	}
	 	JSONObject json = postBody(id,name,email,gender,status);
		
		request.body(json.toJSONString());
		Response resp= request.post(getWeb()+"public/v1/users");
		int code = resp.getStatusCode();
		int recordID=resp.jsonPath().get("data.id"); // capture the id from the API response because the id in the API 
		//is different than the one in the DB in order to be able to use it later for other calls
		//System.out.println(recordID);
		s.executeUpdate("update users set id=" + recordID + ",PostedByEndpoint=1 where id="+id);
		Assert.assertEquals(code, 201);
	} 
	
	@Test
	public void PostUserFail1() throws SQLException, IOException
	{
		

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=1 ORDER BY RAND() limit 1");
	 	int id = 0;
	 	String name="",email="",gender="",status="";
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 		name=rs.getString("name");
	 		email=rs.getString("email");
	 		gender=rs.getString("gender");
	 		status=rs.getString("Status");
	 	}
	 	JSONObject json = postBody(id,name,email,gender,status);
		request.body(json.toJSONString());
		Response resp= request.post(getWeb()+"public/v1/users");
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 422);
	
	} 
	
	@Test
	public void PostUserFail2() throws SQLException, IOException
	{

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=0 and name is null or email is null or gender is null or status is null ORDER BY RAND() limit 1");
	 	int id = 0;
	 	String name="",email="",gender="",status="";
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 		name=rs.getString("name");
	 		email=rs.getString("email");
	 		gender=rs.getString("gender");
	 		status=rs.getString("Status");
	 	}
	 	JSONObject json = postBody(id,name,email,gender,status);
		
		request.body(json.toJSONString());
		Response resp= request.post(getWeb()+"public/v1/users");
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 422);
	
	} 
			

	
	
}


